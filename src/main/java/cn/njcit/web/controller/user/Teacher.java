package cn.njcit.web.controller.user;

import java.util.Date;

/**
 * Created by YK on 2014/9/20.
 */
public class Teacher {
    private String teacherId;
    private String teacherWorkNum;
    private String teacherName;
    private String colleageId;
    private String password;
    private String createTime;

    public Teacher() {
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherWorkNum() {
        return teacherWorkNum;
    }

    public void setTeacherWorkNum(String teacherWorkNum) {
        this.teacherWorkNum = teacherWorkNum;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getColleageId() {
        return colleageId;
    }

    public void setColleageId(String colleageId) {
        this.colleageId = colleageId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;

    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
