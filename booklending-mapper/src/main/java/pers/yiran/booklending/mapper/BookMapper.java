package pers.yiran.booklending.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import pers.yiran.booklending.entity.Book;
import pers.yiran.booklending.entity.Record;

import java.util.List;

/**
 * @author Yiran
 */
@Repository
@Mapper
public interface BookMapper {
    List<Book> getBookList();
    Integer deleteBook(int bookId);
    List<Book> getBookListNotReader();
    Book selectOneById(int id);
    Integer setStatus(int id, String status);
    Integer bookUpdate(Book book);
    Integer bookBorrow(Record record);
    Integer getBookNum(Integer id);
    Integer updateBookNum(Integer id, Integer num);
}
