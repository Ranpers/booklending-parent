package pers.yiran.booklending.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.yiran.booklending.common.Access;
import pers.yiran.booklending.common.AccessLevel;
import pers.yiran.booklending.entity.User;
import pers.yiran.booklending.service.RecordService;

import java.io.IOException;
import java.util.List;

/**
 * @author Yiran
 */
@Controller
@RequestMapping("/record")
public class RecordController {
    private RecordService recordService;
    private final ObjectMapper om = new ObjectMapper();

    @Autowired
    public void setRecordService(RecordService recordService) {
        this.recordService = recordService;
    }

    @Access(level = AccessLevel.EMPLOYEE)
    @GetMapping("/list/{page}")
    public String getRecordList(@PathVariable int page, HttpServletRequest request) {
        List<Object> list = recordService.getRecordList(page);
        request.setAttribute("records", list.get(0));
        request.setAttribute("maxPage", list.get(1));
        return "records";
    }

    @Access(level = AccessLevel.READER)
    @GetMapping("/personal_list/{page}")
    public String getPersonalRecordList(@PathVariable int page, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        List<Object> list = recordService.getPersonalRecordList(user.getId(), page, null);
        request.setAttribute("records", list.get(0));
        request.setAttribute("maxPage", list.get(1));
        request.setAttribute("pageFlag", "1");
        return "personal_records";
    }

    @Access(level = AccessLevel.READER)
    @GetMapping("/personal_not_returned/{page}")
    public String getPersonalNotReturnedRecordList(@PathVariable int page, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        List<Object> list = recordService.getPersonalRecordList(user.getId(), page, "1");
        request.setAttribute("records", list.get(0));
        request.setAttribute("maxPage", list.get(1));
        request.setAttribute("pageFlag", "2");
        return "personal_records";
    }

    @Access(level = AccessLevel.READER)
    @GetMapping("/return/{recordId}")
    public void returnBook(@PathVariable int recordId, HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        try {
            if (recordService.returnBook(user.getId(), recordId) == 1) {
                response.getWriter().write(om.writeValueAsString("update_success"));
            } else {
                response.getWriter().write(om.writeValueAsString("system_error"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}