package cn.njcit.web.controller.user;

import cn.njcit.web.domain.DataTableForm;

/**
 * Created by YK on 2014-09-21.
 */
public class TClassQueryForm extends DataTableForm{
    private String userId;
    private String classId;
    private String className;
    private String  professionalId;
    private String  colleageId;
    private String  createTime;
    private String  colleageName;
    private int managed;//0 未负责 1 已负责

    public TClassQueryForm() {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
