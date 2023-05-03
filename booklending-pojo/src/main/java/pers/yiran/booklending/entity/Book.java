package pers.yiran.booklending.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Yiran
 */
@Getter
@Setter
@ToString
public class Book {
    private int id;
    private String bookname;
    private String isbn;
    private String press;
    private String author;
    private int pagination;
    private double price;
    private String uploadTime;
    private String status;
    private int remainNum;
}