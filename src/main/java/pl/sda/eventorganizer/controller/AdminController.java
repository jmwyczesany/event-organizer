package pl.sda.eventorganizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventorganizer.service.UserService;

@Controller
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/adminProfile")
     public ModelAndView getAdminProfileMainPage() {
        ModelAndView adminProfile = new ModelAndView("adminProfile");




        return adminProfile;
    }

}
