package pers.yiran.booklending.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.yiran.booklending.entity.User;
import pers.yiran.booklending.service.UserService;

import java.io.IOException;
import java.util.List;

/**
 * @author Yiran
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public void toHomePage(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        try {
            if ("ADMIN".equals(user.getRole())) {
                response.sendRedirect("/booklending/admin/home");
            } else {
                response.sendRedirect("/booklending/reader/home");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/login")
    public String toLoginPage(HttpServletRequest request) {
        boolean isRedirect = request.getSession().getAttribute("isRedirect") != null;
        if (isRedirect) {
            request.getSession().removeAttribute("isRedirect");
            request.setAttribute("isRedirect", "1");
        }
        return "login";
    }

    /**
     * 登录验证
     */
    @PostMapping("/login_verification")
    public void login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        List<Object> list = userService.login(user);
        ObjectMapper om = new ObjectMapper();
        try {
            if ((int) list.get(0) == 0) {
                request.getSession().setAttribute("USER_SESSION", list.get(1));
                response.getWriter().write(om.writeValueAsString("login_success"));
            } else if ((int) list.get(0) == 1) {
                response.getWriter().write(om.writeValueAsString("email_not_exist"));
            } else {
                response.getWriter().write(om.writeValueAsString("password_incorrect"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}