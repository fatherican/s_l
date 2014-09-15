package cn.njcit.web.controller.user;

import cn.njcit.web.domain.DataTableForm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by YK on 2014/9/11.
 */
public class UserQueryForm  extends DataTableForm implements Serializable{
    private String userId;
    private String userNo;
    private String password;


    private List<Map> managedClassList ;//负责的班级列表
    private String colleageId;//负责的学院Id

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

    public List<Map> getManagedClassList() {
        return managedClassList;
    }

    public void setManagedClassList(List<Map> managedClassList) {
        this.managedClassList = managedClassList;
    }

    public String getColleageId() {
        return colleageId;
    }

    public void setColleageId(String colleageId) {
        this.colleageId = colleageId;
    }
}
