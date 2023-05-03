package pers.yiran.booklending.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.yiran.booklending.entity.Book;
import pers.yiran.booklending.entity.Record;
import pers.yiran.booklending.mapper.BookMapper;
import pers.yiran.booklending.service.BookService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Yiran
 */
@Service
public class BookServiceImpl implements BookService {
    private BookMapper bookMapper;

    //注入bookMapper
    @Autowired
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public List<Object> getBookList(int pageNum) {
        List<Object> list = new ArrayList<>();
        Page<Book> page = PageHelper.startPage(pageNum, 10);
        list.add(bookMapper.getBookList());
        list.add(page.getPages());
        return list;
    }


    @Override
    public List<Object> getBookListNotReader(int pageNum) {
        List<Object> list = new ArrayList<>();
        Page<Book> page = PageHelper.startPage(pageNum, 8);
        list.add(bookMapper.getBookListNotReader());
        list.add(page.getPages());
        return list;
    }

    @Override
    public Book selectOneById(int id) {
        return bookMapper.selectOneById(id);
    }

    @Override
    public Integer setStatus(int id, String status) {
        return bookMapper.setStatus(id, status);
    }

    @Override
    public Integer bookUpdate(Book book) {
        return bookMapper.bookUpdate(book);
    }

    // TODO: 进行事务处理
    @Override
    public Integer borrowBook(Record record) {
        int bookId = record.getBookId();
        int bookNum = bookMapper.getBookNum(bookId);
        if (bookNum > 0) {
            record.setStatus("1");
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            record.setBorrowDate(dateFormat.format(date));
            int res = bookMapper.bookBorrow(record);
            bookMapper.updateBookNum(bookId, bookNum - 1);
            return res;
        } else {
            return 2;
        }
    }
}