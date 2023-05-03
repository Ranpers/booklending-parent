package pers.yiran.booklending.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.yiran.booklending.common.Access;
import pers.yiran.booklending.common.AccessLevel;
import pers.yiran.booklending.entity.Book;
import pers.yiran.booklending.entity.Record;
import pers.yiran.booklending.entity.User;
import pers.yiran.booklending.service.BookService;

import java.io.IOException;
import java.util.List;

/**
 * @author Yiran
 */
@Controller
@RequestMapping("/book")
public class BookController {
    private BookService bookService;
    private final ObjectMapper om = new ObjectMapper();

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * 删除特定图书 最低权限ADMIN
     */
    @Access(level = AccessLevel.ADMIN)
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable int id, HttpServletResponse response) {
        if (bookService.deleteBook(id) == 1) {
            try {
                response.getWriter().write(om.writeValueAsString("delete_success"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获取已启用图书列表 最低权限READER
     */
    @Access(level = AccessLevel.READER)
    @GetMapping("/list/{page}")
    public String getBookList(@PathVariable int page, HttpServletRequest request) {
        List<Object> list = bookService.getBookList(page);
        request.setAttribute("books", list.get(0));
        request.setAttribute("maxPage", list.get(1));
        return "books";
    }

    /**
     * 获取所有图书列表 最低权限EMPLOYEE
     */
    @Access(level = AccessLevel.EMPLOYEE)
    @GetMapping("/list_not_reader/{page}")
    public String getBookListNotReader(@PathVariable int page, HttpServletRequest request) {
        List<Object> list = bookService.getBookListNotReader(page);
        request.setAttribute("books", list.get(0));
        request.setAttribute("maxPage", list.get(1));
        return "books";
    }

    /**
     * 更改图书状态 最低权限EMPLOYEE
     */
    @Access(level = AccessLevel.EMPLOYEE)
    @GetMapping("/status/{id}/{status}")
    public void setStatus(@PathVariable int id, @PathVariable String status, HttpServletResponse response) {
        try {
            if (bookService.setStatus(id, status) == 1) {
                response.getWriter().write(om.writeValueAsString("update_success"));
            } else {
                response.getWriter().write(om.writeValueAsString("no_permissions"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Access(level = AccessLevel.READER)
    @PostMapping("/borrow")
    public void borrowBook(@RequestBody Record record, HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        int userId = user.getId();
        record.setBorrowerId(userId);
        int res = bookService.borrowBook(record);
        try {
            if (res == 1) {
                response.getWriter().write(om.writeValueAsString("borrow_success"));
            } else if(res == 2){
                response.getWriter().write(om.writeValueAsString("borrow_out_range"));
            } else {
                response.getWriter().write(om.writeValueAsString("no_permissions"));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * 获取特定图书信息 最低权限:EMPLOYEE
     */
    @Access(level = AccessLevel.EMPLOYEE)
    @GetMapping("/one/{id}")
    public void getSelectReaderData(@PathVariable int id, HttpServletResponse response) {
        Book book = bookService.selectOneById(id);
        try {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(om.writeValueAsString(book));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 编辑特定图书信息 最低权限:EMPLOYEE
     */
    @Access(level = AccessLevel.EMPLOYEE)
    @PostMapping("/one/update")
    public void bookUpdate(@RequestBody Book book, HttpServletResponse response) {
        try {
            if (bookService.bookUpdate(book) == 1) {
                response.getWriter().write(om.writeValueAsString("update_success"));
            } else {
                response.getWriter().write(om.writeValueAsString("no_permissions"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}