package pers.yiran.booklending.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import pers.yiran.booklending.entity.User;
import pers.yiran.booklending.model.UserModel;

import java.util.List;
import java.util.Map;

/**
 * @author Yiran
 */
@Repository
@Mapper
public interface UserMapper {
    UserModel select(User user);
    User login(User user);
    Integer delete(int id);
    Integer update(User user);
    List<User> getUserList(int row);
}
