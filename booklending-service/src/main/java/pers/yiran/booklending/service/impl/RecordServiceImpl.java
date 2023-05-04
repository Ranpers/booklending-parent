package pers.yiran.booklending.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.yiran.booklending.entity.Book;
import pers.yiran.booklending.mapper.BookMapper;
import pers.yiran.booklending.mapper.RecordMapper;
import pers.yiran.booklending.model.RecordModel;
import pers.yiran.booklending.service.RecordService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yiran
 */
@Service
public class RecordServiceImpl implements RecordService {
    private RecordMapper recordMapper;
    private BookMapper bookMapper;

    @Autowired
    public void setRecordMapper(RecordMapper recordMapper) {
        this.recordMapper = recordMapper;
    }

    @Autowired
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public List<Object> getRecordList(int pageNum) {
        List<Object> list = new ArrayList<>();
        Page<RecordModel> page = PageHelper.startPage(pageNum, 8);
        List<RecordModel> recordModels = recordMapper.getRecordList();
        list.add(recordModels);
        list.add(page.getPages());
        return list;
    }

    @Override
    public Integer returnBook(int userId, int recordId) {
        return recordMapper.returnBook(userId, recordId);
    }

    @Override
    public List<Object> getNotApproval(int pageNum) {
        List<Object> list = new ArrayList<>();
        Page<RecordModel> page = PageHelper.startPage(pageNum, 10);
        List<RecordModel> recordModels = recordMapper.getNotApproval();
        list.add(recordModels);
        list.add(page.getPages());
        return list;
    }

    @Override
    public List<Object> getPersonalRecordList(int userId, int pageNum, String status) {
        List<Object> list = new ArrayList<>();
        Page<RecordModel> page = PageHelper.startPage(pageNum, 8);
        List<RecordModel> recordModels;
        if (status == null) {
            recordModels = recordMapper.getPersonalRecordList(userId);
        } else {
            recordModels = recordMapper.getPersonalNotReturnedList(userId);
        }
        list.add(recordModels);
        list.add(page.getPages());
        return list;
    }

    @Override
    public Integer returnApproval(int recordId) {
        if(recordMapper.returnApproval(recordId) == 1){
            RecordModel recordModel = recordMapper.getRecordById(recordId);
            Book book = recordModel.getBook();
            return bookMapper.updateBookNum(book.getId(), book.getRemainNum() + 1);
        }
        return 0;
    }
}