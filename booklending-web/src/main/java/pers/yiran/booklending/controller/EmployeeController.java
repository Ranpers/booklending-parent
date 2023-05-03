package pers.yiran.booklending.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.yiran.booklending.common.Access;
import pers.yiran.booklending.common.AccessLevel;
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
     * 编辑特定员工信息 最低权限:ADMIN
     */
    @Access(level = AccessLevel.ADMIN)
    @PostMapping("/one/update")
    public void employeeUpdate(@RequestBody UserModel user, HttpServletRequest request, HttpServletResponse response) {
        user.setRole(1);
        try {
            if (userService.update(user) == 1) {
                response.getWriter().write(om.writeValueAsString("update_success"));
            } else {
                request.getRequestDispatcher("/permission/no_permissions").forward(request, response);
            }
        } catch (IOException | ServletException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取特定员工信息 最低权限:ADMIN
     */
    @Access(level = AccessLevel.ADMIN)
    @GetMapping("/one/{id}")
    public void getSelectReaderData(@PathVariable int id, HttpServletResponse response){
        UserModel user = userService.select(1, id);
        try {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(om.writeValueAsString(user));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Access(level = AccessLevel.ADMIN)
    @PostMapping("/save")
    public void saveEdited(@RequestBody UserModel userModel){

    }
}
