package pers.yiran.booklending.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Yiran
 */
@Getter
@Setter
@ToString
public class UserModel {
    private Integer id;       //用户id
    private String username;  //用户名称
    private String gender;    //性别
    private String birthday;  //生日
    private String phone;     //手机
    private Integer role;     //角色
}
