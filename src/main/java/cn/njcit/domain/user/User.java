package cn.njcit.domain.user;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YK on 2014-06-15.
 */
public class User implements Serializable{
    private static final long serialVersionUID = 6977415643848374753L;
    private String userId;
    private String userNo;//用户的登陆名，对应老师就是工号，对应学生就是学号
    private String name;//用户的姓名
    private String password;
    private Integer role;//角色
    private Integer colleageId;//学院ID
    private String colleageName;
    private String prefix;
    //学生应该有的字段
    private Integer classId;//班级ID
    private String className;//班级名称
    private Integer professionalId;//专业Id

    private String token;
//    private List<SClass> managedClassList;
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

    public Integer getColleageId() {
        return colleageId;
    }

    public void setColleageId(Integer colleageId) {
        this.colleageId = colleageId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(Integer professionalId) {
        this.professionalId = professionalId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

//    public List<SClass> getManagedClassList() {
//        return managedClassList;
//    }
//
//    public void setManagedClassList(List<SClass> managedClassList) {
//        this.managedClassList = managedClassList;
//    }

    public String getColleageName() {
        return colleageName;
    }

    public void setColleageName(String colleageName) {
        this.colleageName = colleageName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
