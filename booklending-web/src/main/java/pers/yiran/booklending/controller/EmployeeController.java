package pers.yiran.booklending.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.yiran.booklending.common.Access;
import pers.yiran.booklending.common.AccessLevel;
import pers.yiran.booklending.entity.User;
import pers.yiran.booklending.model.UserModel;
import pers.yiran.booklending.service.UserService;

import java.io.IOException;
import java.util.List;

/**
 * @author Yiran
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private final ObjectMapper om = new ObjectMapper();
    private UserService userService;

    /**
     * 获取员工列表 最低权限:ADMIN
     */
    @Access(level = AccessLevel.ADMIN)
    @GetMapping("/list/{page}")
    public String getEmployeeList(@PathVariable int page, HttpServletRequest request) {
        List<Object> list = userService.getUserList(page, 1);
        request.setAttribute("users", list.get(0));
        request.setAttribute("maxPage", list.get(1));
        request.setAttribute("role", "employee");
        return "users";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 编辑员工信息 最低权限:ADMIN
     */
    @Access(level = AccessLevel.ADMIN)
    @GetMapping("/edit")
    public void readerEdit(@RequestBody User user) {
        user.setRole(1);
        userService.update(user);
    }

    /**
     * 获取特定员工信息 最低权限:ADMIN
     */
    @Access(level = AccessLevel.ADMIN)
    @GetMapping("/one/{id}")
    public void getSelectReaderData(@PathVariable int id, HttpServletResponse response){
        UserModel user = userService.select(1, id);
        try {
            response.getWriter().write(om.writeValueAsString(user));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
