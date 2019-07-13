package pl.sda.eventorganizer.controller;


import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventorganizer.dto.RegisterForm;
import pl.sda.eventorganizer.service.UserService;
import pl.sda.eventorganizer.service.UserValidator;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final UserValidator userValidator;

    public RegistrationController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("registration")
    public ModelAndView registrationGetData() {
        return new ModelAndView("registration").addObject("registerForm", new RegisterForm());
    }

    @PostMapping("registration")
    public ModelAndView registrationPostData(@ModelAttribute RegisterForm registerForm, BindingResult bindingResult) {
        userValidator.validate(registerForm,bindingResult);

        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("registration");
            modelAndView.addObject("error", bindingResult.getFieldError().getDefaultMessage());
            return modelAndView;
        }
        userService.save(registerForm.getEmail(), registerForm.getUserName(), registerForm.getPassword(), registerForm.getConfirmPassword(), registerForm.getRole());

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("accountCreated", registerForm.getEmail());

        return modelAndView;
    }


}
