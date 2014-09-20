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

    private String role;//角色
    private String name; //姓名
    private String colleageName;//学院的名字

    private List<String> roles;//

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColleageName() {
        return colleageName;
    }

    public void setColleageName(String colleageName) {
        this.colleageName = colleageName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
