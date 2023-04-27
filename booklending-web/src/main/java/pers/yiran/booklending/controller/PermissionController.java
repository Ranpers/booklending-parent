package pers.yiran.booklending.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Yiran
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
    @GetMapping("/no_permissions")
    public String toNoPermissionsPage(){
        return "no_permissions";
    }
}
