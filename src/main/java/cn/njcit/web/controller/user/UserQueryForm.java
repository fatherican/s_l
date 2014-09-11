package cn.njcit.web.controller.user;

import java.io.Serializable;

/**
 * Created by YK on 2014/9/11.
 */
public class UserQueryForm implements Serializable{
    private String userId;
    private String userNo;
    private String password;

    public UserQueryForm() {
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
