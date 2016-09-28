package com.arms.app.user;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

/**
 * Created by arms_matsushita on 西暦2016/09/24.
 */
@Controller
public class LoginController {

    /**
     *
     * @param principal
     * @param modelAndView
     * @return
     */
    @RequestMapping("/user/login")
    public ModelAndView login(Principal principal, ModelAndView modelAndView) {
        if(principal != null){
            modelAndView.setViewName("redirect:/user/logout");
            return modelAndView;
        }
        modelAndView.setViewName("/user/login");
        return modelAndView;
    }

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/failLogin", method = RequestMethod.GET)
    public ModelAndView failLogin(ModelAndView model) {
        model.addObject("errorMsg", "Login fail, because id or password, or both fail.");
        model.addObject("loginFail", true);
        model.setViewName("/user/login");
        return model;
    }

    /**
     *
     * @param model
     * @param bindingResult
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping(value = "/user/successLogin", method = RequestMethod.GET)
    public Object successLogin(ModelAndView model, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasErrors()){
            model.setViewName("/user/login");
            return model;
        }
        redirectAttributes.addFlashAttribute("message", "Login Successful");
        model.addObject("loginSuccess", true);
        return "redirect:/";
    }
}
