package pers.yiran.booklending.service;

import pers.yiran.booklending.entity.Book;

import java.util.List;

/**
 * @author Yiran
 */
public interface BookService {
    List<Object> getBookList(int page);
    List<Object> getBookListNotReader(int page);
    Book selectOneById(int id);
    Integer setStatus(int id, String status);
    Integer bookUpdate(Book book);
}
