package cn.njcit.domain.user;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created by YK on 2014-06-15.
 */
public class User implements Serializable{
    private String userId;
    private String userNo;//用户的登陆名，对应老师就是工号，对应学生就是学号
    private String name;//用户的姓名
    private String password;
    private Integer role;//角色

    public String getUserId() {
        if(StringUtils.isEmpty(userId)) return "";
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        if(StringUtils.isEmpty(name)) return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        if(StringUtils.isEmpty(password)) return "";
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
}
