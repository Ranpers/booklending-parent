package pers.yiran.booklending.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.yiran.booklending.entity.User;
import pers.yiran.booklending.mapper.UserMapper;
import pers.yiran.booklending.model.UserModel;
import pers.yiran.booklending.service.UserService;

import java.util.ArrayList;;
import java.util.List;

/**
 * @author Yiran
 */
@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;

    //注入userMapper
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserModel select(int role, int id) {
        User user = new User();
        user.setRole(role);
        user.setId(id);
        return userMapper.select(user);
    }

    @Override
    public List<Object> login(User u) {
        User user = userMapper.login(u);
        List<Object> list = new ArrayList<>();
        if (user == null) {
            //邮箱不存在
            list.add(1);
            return list;
        }
        if (user.getPassword().equals(u.getPassword())) {
            //验证成功
            list.add(0);
            list.add(user);
        } else {
            //密码错误
            list.add(2);
        }
        return list;
    }

    @Override
    public Integer delete(int userId) {
        return userMapper.delete(userId);
    }

    @Override
    public Integer update(UserModel userModel) {
        return userMapper.update(userModel);
    }
    @Override
    public List<Object> getUserList(int pageNum, int role) {
        List<Object> list = new ArrayList<>();
        Page<User> page = PageHelper.startPage(pageNum, 5);
        list.add(userMapper.getUserList(role));
        list.add(page.getPages());
        return list;
    }
}