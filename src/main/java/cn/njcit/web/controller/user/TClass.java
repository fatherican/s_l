package cn.njcit.web.controller.user;

import java.io.Serializable;

/**
 * Created by YK on 2014/9/17.
 */
public class TClass implements Serializable{
    private String classId;
    private String className;
    private String  professionalId;
    private String  colleageId;
    private String  createTime;
    private String  colleageName;
    private int managed;//0  其他老师负责 1 当前老师正在负责 2 未有老师负责
    //负责该班级老的的名字
    private String userName;
    //负责该班级老师的 工号
    private String userNo;

    public TClass() {
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public String getColleageId() {
        return colleageId;
    }

    public void setColleageId(String colleageId) {
        this.colleageId = colleageId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getColleageName() {
        return colleageName;
    }

    public void setColleageName(String colleageName) {
        this.colleageName = colleageName;
    }

    public int getManaged() {
        return managed;
    }

    public void setManaged(int managed) {
        this.managed = managed;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
}
