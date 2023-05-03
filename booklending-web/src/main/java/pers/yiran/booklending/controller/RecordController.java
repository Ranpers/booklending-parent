package pers.yiran.booklending.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.yiran.booklending.common.Access;
import pers.yiran.booklending.common.AccessLevel;
import pers.yiran.booklending.service.RecordService;

import java.util.List;

/**
 * @author Yiran
 */
@Controller
@RequestMapping("/record")
public class RecordController {
    RecordService recordService;
    @Autowired
    public void setRecordService(RecordService recordService){
        this.recordService = recordService;
    }
    @Access(level = AccessLevel.EMPLOYEE)
    @RequestMapping("/list/{page}")
    public String getRecordList(@PathVariable int page, HttpServletRequest request){
        List<Object> list = recordService.getRecordList(page);
        request.setAttribute("records", list.get(0));
        request.setAttribute("maxPage", list.get(1));
        return "records";
    }
}