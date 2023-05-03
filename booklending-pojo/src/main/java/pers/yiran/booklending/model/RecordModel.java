package pers.yiran.booklending.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pers.yiran.booklending.entity.Book;

/**
 * @author Yiran
 */
@Getter
@Setter
@ToString
public class RecordModel {
    private Book book;
    private UserModel user;
    private Integer id;
    private String status;
    private String borrowDate;
    private String remandDate;
}