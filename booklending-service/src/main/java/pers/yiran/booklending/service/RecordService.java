package pers.yiran.booklending.service;

import java.util.List;

/**
 * @author Yiran
 */
public interface RecordService {
    List<Object> getRecordList(int page);
    List<Object> getPersonalRecordList(int userId, int page);
}
