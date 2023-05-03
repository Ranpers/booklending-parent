package pers.yiran.booklending.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import pers.yiran.booklending.entity.Book;

import java.util.List;

/**
 * @author Yiran
 */
@Repository
@Mapper
public interface BookMapper {
    List<Book> getBookList();
    List<Book> getBookListNotReader();
    Book selectOneById(int id);
    Integer setStatus(int id, String status);
    Integer bookUpdate(Book book);
}
