package pers.yiran.booklending.service;

import java.util.List;

/**
 * @author Yiran
 */
public interface RecordService {
    List<Object> getRecordList(int page);
    Integer returnBook(int userId, int recordId);
    List<Object> getNotApproval(int page);

    List<Object> getPersonalRecordList(int userId, int page, String status);
    Integer returnApproval(int recordId);
}
