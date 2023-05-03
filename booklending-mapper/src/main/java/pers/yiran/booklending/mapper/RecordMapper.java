package pers.yiran.booklending.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import pers.yiran.booklending.model.RecordModel;

import java.util.List;

/**
 * @author Yiran
 */
@Repository
@Mapper
public interface RecordMapper {
    List<RecordModel> getRecordList();
    List<RecordModel> getPersonalRecordList(int userId);
    Integer addRecord();
}
