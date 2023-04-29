package pers.yiran.booklending.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import pers.yiran.booklending.entity.User;

/**
 * @author Yiran
 */
@Repository
@Mapper
public interface UserMapper {
    User login(User user);
    Integer delete(int id);
}
