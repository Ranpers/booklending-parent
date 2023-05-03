package pers.yiran.booklending.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Yiran
 */
@Setter
@Getter
@ToString
public class Record {
    private Integer id;
    private String status;
    private Integer borrowerId;
    private Integer bookId;
    private String borrowDate;
    private String remandDate;
}