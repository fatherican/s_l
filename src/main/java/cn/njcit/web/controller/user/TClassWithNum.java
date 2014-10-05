package cn.njcit.web.controller.user;

import java.io.Serializable;

/**
 * Created by YK on 2014/9/17.
 */
public class TClassWithNum implements Serializable{
    private String className;
    private String  colleageId;
    private String  rowNum;


    public TClassWithNum() {
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getColleageId() {
        return colleageId;
    }

    public void setColleageId(String colleageId) {
        this.colleageId = colleageId;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }
}
