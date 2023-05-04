package pers.yiran.booklending.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.yiran.booklending.common.Access;
import pers.yiran.booklending.common.AccessLevel;
import pers.yiran.booklending.entity.User;
import pers.yiran.booklending.service.UserService;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Yiran
 * 用户包括 Reader、Employee、Admin
 * 此类下方法为各类用户所共有的操作
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private final ObjectMapper om = new ObjectMapper();

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Access(level = AccessLevel.READER)
    @GetMapping("/home")
    public String toHomePage(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        if (user.getRole() == 0) {
            return "home_reader";
        } else {
            return "home_not_reader";
        }
    }

    // TODO: 当点击顶栏中的选项重定向到登录页面时，顶栏不消失
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
        try {
            if ((int) list.get(0) == 0) {
                // TODO: 不要直接存储密码，考虑进行加密！
                User u = (User) list.get(1);
                HttpSession session = request.getSession();
                request.getServletContext().setAttribute("userId:" + u.getId(), session);
                request.getSession().setAttribute("USER_SESSION", u);
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

    @Access(level = AccessLevel.READER)
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("USER_SESSION");
        try {
            response.sendRedirect("/booklending/user/login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Access(level = AccessLevel.ADMIN)
    @GetMapping("/status/{id}/{status}")
    public void setStatus(@PathVariable int id, @PathVariable String status, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (userService.setStatus(id, status) == 1) {
                if ("1".equals(status)) {
                    ServletContext servletContext = request.getServletContext();
                    Enumeration<String> attributeNames = servletContext.getAttributeNames();
                    while (attributeNames.hasMoreElements()) {
                        String attributeName = attributeNames.nextElement();
                        if (("userId:" + id).equals(attributeName)) {
                            HttpSession session = (HttpSession) servletContext.getAttribute(attributeName);
                            servletContext.removeAttribute("userId:" + id);
                            session.invalidate();
                            response.getWriter().write(om.writeValueAsString("user_off"));
                        }
                    }
                } else if ("0".equals(status)) {
                    response.getWriter().write(om.writeValueAsString("user_on"));
                }
            } else {
                response.getWriter().write(om.writeValueAsString("system_error"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Access(level = AccessLevel.ADMIN)
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable int id, HttpServletResponse response) {
        if (userService.delete(id) == 1) {
            try {
                response.getWriter().write(om.writeValueAsString("delete_success"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}