package pl.sda.eventorganizer.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorController implements ErrorController {


    @Override
    public String getErrorPath() {
       return "/error";
    }

    @GetMapping("/error")
    ModelAndView getErrorPage(HttpServletResponse response){

        int status = response.getStatus();
        System.out.println("Something like this happened " + status);
        return new ModelAndView("/error");
    }


}
