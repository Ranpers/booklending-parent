package pers.yiran.booklending.service;

import pers.yiran.booklending.entity.User;

import java.util.List;

/**
 * @author Yiran
 */
public interface UserService {
    List<Object> login(User user);
    Integer delete(int userId);
}
