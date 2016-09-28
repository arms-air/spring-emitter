package com.arms.app.user;

import com.arms.domain.component.ControllerAspect;
import com.arms.domain.component.PageWrapper;
import com.arms.domain.entity.Micropost;
import com.arms.domain.entity.User;
import com.arms.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;

/**
 * Created by arms_matsushita on 西暦2016/09/26.
 */
@Controller
public class UserController {

    @Autowired
    ControllerAspect controllerAspect;

    @Autowired
    UserService userService;

    /**
     *
     * @param userId
     * @param modelAndView
     * @param pageable
     * @return
     */
    @RequestMapping("/user/show/{userId}")
    public ModelAndView show(@PathVariable int userId, ModelAndView modelAndView, Pageable pageable, Principal principal) {
        modelAndView.addObject("followings", userService.getFollowingListByUserId(userId));
        modelAndView.addObject("followers", userService.getFollowerListByUserId(userId));
        User user = userService.findOne(userId);
        modelAndView.addObject("user", user);
        User loginUser = userService.findOne(principal);
        // modelAndView.addObject("loginUser", loginUser);
        if(user.getId() != loginUser.getId()) {
            modelAndView.addObject("isFollow", userService.isFollow(user.getId(), loginUser.getId()));
            modelAndView.addObject("userFollowForm", userService.getUserFollowForm(user.getId(), loginUser.getId()));
        }

        Page<Micropost> micropostPage = userService.findAllMicropostByUserId(userId, pageable);
        PageWrapper<Micropost> page = new PageWrapper<>(micropostPage, "/");
        modelAndView.addObject("microposts", page.getContent());
        modelAndView.addObject("page", page);
        modelAndView.setViewName("/user/show");
        return modelAndView;
    }

    /**
     *
     * @param modelAndView
     * @param pageable
     * @return
     */
    @RequestMapping("/user/list")
    public ModelAndView list(ModelAndView modelAndView, Principal principal, Pageable pageable) {
        Page<User> userPage = userService.findAllUser(pageable);
        PageWrapper<User> page = new PageWrapper<>(userPage, "/user/list");
        modelAndView.addObject("users", page.getContent());
        modelAndView.addObject("page", page);
        modelAndView.addObject("userId", userService.getUserId(principal));
        modelAndView.setViewName("/user/list");
        return modelAndView;
    }

    /**
     *
     * @param userId
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/user/edit/{userId}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable int userId, ModelAndView modelAndView, Principal principal) {
        modelAndView.addObject("user", userService.findOne(userId));
        modelAndView.addObject("userEditForm", userService.setUserEditForm(userId));
        modelAndView.setViewName("/user/edit");
        return modelAndView;
    }

    /**
     *
     * @param userEditForm
     * @param bindingResult
     * @param redirectAttributes
     * @param modelAndView
     * @return
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public Object edit(@Validated UserEditForm userEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, ModelAndView modelAndView, Principal principal) throws NoSuchAlgorithmException {

        if(bindingResult.hasErrors()) {
            modelAndView.addObject("user", userService.findOne(userEditForm.getUserId()));
            modelAndView.setViewName("/user/edit");
            return modelAndView;
        }

        userService.updateUser(userEditForm);
        redirectAttributes.addFlashAttribute("message", "User was updated.");
        return "redirect:/";
    }

    /**
     *
     * @param userId
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/user/delete/{userId}")
    public String delete(@PathVariable int userId, RedirectAttributes redirectAttributes) {
        userService.deleteUser(userId);
        redirectAttributes.addFlashAttribute("message", "User was deleted.");
        return "redirect:/";
    }

    /**
     *
     * @param modelAndView
     * @param principal
     * @return
     */
    @RequestMapping(value = "/user/following/{userId}")
    public ModelAndView following(@PathVariable int userId, ModelAndView modelAndView, Principal principal, Pageable pageable) {
        User user = userService.findOne(userId);
        // User loginUserId = userService.findOne(principal);
        modelAndView.addObject("user", user);
        // modelAndView.addObject("followings", userService.getFollowingListByUserInfo(user.getId()));
        modelAndView.addObject("followers", userService.getFollowerListByUserId(user.getId()));

        Page<User> userPage = userService.findAllFollowing(user.getId(), pageable);
        PageWrapper<User> page = new PageWrapper<>(userPage, "/user/following");
        modelAndView.addObject("followings", page.getContent());
        modelAndView.addObject("page", page);
        modelAndView.setViewName("/user/following");
        return modelAndView;
    }

    /**
     *
     * @param modelAndView
     * @param principal
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/user/followers/{userId}")
    public ModelAndView follower(@PathVariable int userId, ModelAndView modelAndView, Principal principal, Pageable pageable) {
        // User loginUser = userService.findOne(principal);
        User user = userService.findOne(userId);
        modelAndView.addObject("user", user);
        modelAndView.addObject("followings", userService.getFollowingListByUserId(user.getId()));

        Page<User> userPage = userService.findAllFollower(user.getId(), pageable);
        PageWrapper<User> page = new PageWrapper<>(userPage, "/user/followers");
        modelAndView.addObject("followers", page.getContent());
        modelAndView.addObject("page", page);
        modelAndView.setViewName("/user/followers");
        return modelAndView;
    }

    /**
     *
     * @param userFollowForm
     * @return
     */
    @RequestMapping(value = "/user/follow")
    public String follow(@ModelAttribute("userFollowForm") UserFollowForm userFollowForm) {
        userService.addFollow(userFollowForm);
        return "redirect:/user/show/" + userFollowForm.getUserId();
    }

    /**
     *
     * @param userFollowForm
     * @return
     */
    @RequestMapping(value = "/user/unfollow")
    public String unFollow(@ModelAttribute("userFollowForm") UserFollowForm userFollowForm) {
        userService.deleteFollow(userFollowForm);
        return "redirect:/user/show/" + userFollowForm.getUserId();
    }
}
