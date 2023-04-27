package pers.yiran.booklending.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Yiran
 */
@Getter
@Setter
@ToString
public class User {
    private Integer id;       //用户id
    private String username;  //用户名称
    private String password;  //用户密码
    private String email;     //用户邮箱（用户账号）
    private String role;      //用户角色
    private String status;    //用户状态
}