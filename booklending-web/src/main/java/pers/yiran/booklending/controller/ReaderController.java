package pers.yiran.booklending.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.yiran.booklending.common.Access;
import pers.yiran.booklending.common.AccessLevel;

/**
 * @author Yiran
 */
@Controller
@RequestMapping("/reader")
public class ReaderController {
    @Access(level = AccessLevel.READER)
    @GetMapping("/home")
    public String toHomePage(){
        return "home_reader";
    }
}
