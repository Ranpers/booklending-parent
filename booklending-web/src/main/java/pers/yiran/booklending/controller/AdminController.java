package pers.yiran.booklending.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Yiran
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/home")
    public String toHomePage(){
        return "home_admin";
    }
}
