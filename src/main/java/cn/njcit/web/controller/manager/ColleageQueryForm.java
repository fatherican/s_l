package cn.njcit.web.controller.manager;

import cn.njcit.web.domain.DataTableForm;

import java.io.Serializable;

/**
 * Created by YK on 2014/9/30.
 */
public class ColleageQueryForm extends DataTableForm implements Serializable{

    private String colleageId;
    private String colleageName;
    private String prefix;

    public ColleageQueryForm() {
    }

    public String getColleageId() {
        return colleageId;
    }

    public void setColleageId(String colleageId) {
        this.colleageId = colleageId;
    }

    public String getColleageName() {
        return colleageName;
    }

    public void setColleageName(String colleageName) {
        this.colleageName = colleageName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
