package pl.sda.eventorganizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventorganizer.dto.LoginForm;
import pl.sda.eventorganizer.service.UserService;
import pl.sda.eventorganizer.service.UserValidator;

@Controller
public class LoginController {

    private final UserValidator userValidator;

    public LoginController(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    @GetMapping("login")
    ModelAndView getLoginData(){
        return new ModelAndView("login").addObject("loginForm",new LoginForm());
    }
    @PostMapping("login")
    public ModelAndView loginPost(@ModelAttribute LoginForm loginForm, BindingResult bindingResult) {

        userValidator.validateLogin(loginForm, bindingResult);

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", bindingResult.getFieldError().getDefaultMessage());
            return modelAndView;
        }

        return new ModelAndView("forward:/login");
    }

}
