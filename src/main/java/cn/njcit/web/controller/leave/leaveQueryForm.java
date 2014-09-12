package cn.njcit.web.controller.leave;

import cn.njcit.web.domain.DataTableForm;

/**
 * Created by YK on 2014/9/12.
 */
public class leaveQueryForm  extends DataTableForm{
    private String studentId;//学号
    private String studentName;//班级
    private String colleageName;//学院名称
    private String leaveType;//请假类型  天 或 节次


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getColleageName() {
        return colleageName;
    }

    public void setColleageName(String colleageName) {
        this.colleageName = colleageName;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }
}
