package cn.njcit.web.controller.user;

/**
 * Created by YK on 2014/9/17.
 */
public class Colleage {
    //学院ID
    private String colleageId;
    //学院名称
    private String colleageName;
    //学院前缀
    private String prefix;

    public Colleage() {
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
