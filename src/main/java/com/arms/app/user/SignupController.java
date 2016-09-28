package com.arms.app.user;

import com.arms.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

/**
 * Created by arms_matsushita on 西暦2016/09/24.
 */
@Controller
public class SignupController {

    @Autowired
    UserService userService;

    /**
     * Sign up
     * @return
     */
    @RequestMapping("/user/signUp")
    public ModelAndView signup(ModelAndView modelAndView) {
        modelAndView.setViewName("/user/sign_up");
        modelAndView.addObject("userAddForm", new UserAddForm());
        return modelAndView;
    }

    /**
     * Add new user
     * @param userAddForm
     * @param bindingResult
     * @param attributes
     * @param modelAndView
     * @return
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping("/user/add")
    public Object add(@ModelAttribute("userAddForm") @Valid UserAddForm userAddForm, BindingResult bindingResult,
                      RedirectAttributes attributes, ModelAndView modelAndView) throws NoSuchAlgorithmException {

        if(bindingResult.hasErrors()) {
            return "/user/sign_up";
        }
        userService.createUser(userAddForm);
        attributes.addFlashAttribute("messageDialog", "User was created.");
        return "redirect:/user/login";
    }
}
