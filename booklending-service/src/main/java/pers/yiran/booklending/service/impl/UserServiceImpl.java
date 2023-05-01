package pers.yiran.booklending.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.yiran.booklending.entity.User;
import pers.yiran.booklending.mapper.UserMapper;
import pers.yiran.booklending.service.UserService;

import java.util.ArrayList;
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
    public List<Object> getUserList(int pageNum, int role) {
        Page<User> page = PageHelper.startPage(pageNum, 5);
        List<Object> list = new ArrayList<>();
        list.add(page.getPages());
        list.add(userMapper.getUserList(role));
        return list;
    }
}