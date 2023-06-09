package pers.yiran.booklending.service;

import pers.yiran.booklending.entity.User;
import pers.yiran.booklending.model.UserModel;

import java.util.List;

/**
 * @author Yiran
 */
public interface UserService {
    UserModel select(int role, int id);
    List<Object> login(User user);
    Integer delete(int userId);
    Integer update(UserModel userModel);
    Integer setStatus(int id, String status);
    List<Object> getUserList(int page, int role);
}