package pers.yiran.booklending.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping("/reader")
public class ReaderController {
    private final ObjectMapper om = new ObjectMapper();
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取读者列表 最低权限:EMPLOYEE
     */
    @Access(level = AccessLevel.EMPLOYEE)
    @GetMapping("/list/{page}")
    public String getReaderList(@PathVariable int page, HttpServletRequest request) {
        List<Object> list = userService.getUserList(page, 0);
        request.setAttribute("users", list.get(0));
        request.setAttribute("maxPage", list.get(1));
        request.setAttribute("role", "reader");
        return "users";
    }

    /**
     * 编辑特定读者信息 最低权限:EMPLOYEE
     */
    @Access(level = AccessLevel.EMPLOYEE)
    @PostMapping("/one/update")
    public void readerUpdate(@RequestBody UserModel user, HttpServletResponse response) {
        user.setRole(0);
        try {
            if (userService.update(user) == 1) {
                response.getWriter().write(om.writeValueAsString("update_success"));
            } else {
                response.getWriter().write(om.writeValueAsString("no_permissions"));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取特定读者信息 最低权限:EMPLOYEE
     */
    @Access(level = AccessLevel.EMPLOYEE)
    @GetMapping("/one/{id}")
    public void getSelectReaderData(@PathVariable int id, HttpServletResponse response){
        UserModel user = userService.select(0, id);
        try {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(om.writeValueAsString(user));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
