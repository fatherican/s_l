package cn.njcit.web.controller.leave;

import cn.njcit.web.domain.DataTableForm;

import java.util.List;
import java.util.Map;

/**
 * Created by YK on 2014/9/12.
 */
public class leaveQueryForm  extends DataTableForm{
    private String userNo;//学号
    private String studentName;//班级
    private String colleageName;//学院名称
    private String leaveType;//请假类型  天 或 节次
    private String colleageId;//辅导员负责的学院Id
    private List<Map> classesList;//老师负责的班级
    private String leaveSicked;//假条是否已经被销掉
    private String approved;//总审批状态   总审批状态(-1 未审批0 未通过 1通过 2辅导员已审批等待学管处审批)
    private List<String> approvedStates;//总审批集合状态   总审批状态(-1 未审批0 未通过 1通过 2辅导员已审批等待学管处审批)
    private String instructorApproved;// 辅导员审批状态(-1 未审批0 未通过 1通过)
    private String instructorNotApproved;// !instructorApproved
    private String studentPipeApproved;//学管处审批状态(-1 未审批0 未通过 1通过)
    private String studentNotPipeApproved;// !studentPipeApproved

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
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

    public String getColleageId() {
        return colleageId;
    }

    public void setColleageId(String colleageId) {
        this.colleageId = colleageId;
    }

    public List<Map> getClassesList() {
        return classesList;
    }

    public void setClassesList(List<Map> classesList) {
        this.classesList = classesList;
    }

    public String getLeaveSicked() {
        return leaveSicked;
    }

    public void setLeaveSicked(String leaveSicked) {
        this.leaveSicked = leaveSicked;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getInstructorApproved() {
        return instructorApproved;
    }

    public void setInstructorApproved(String instructorApproved) {
        this.instructorApproved = instructorApproved;
    }

    public String getStudentPipeApproved() {
        return studentPipeApproved;
    }

    public void setStudentPipeApproved(String studentPipeApproved) {
        this.studentPipeApproved = studentPipeApproved;
    }

    public String getInstructorNotApproved() {
        return instructorNotApproved;
    }

    public void setInstructorNotApproved(String instructorNotApproved) {
        this.instructorNotApproved = instructorNotApproved;
    }

    public String getStudentNotPipeApproved() {
        return studentNotPipeApproved;
    }

    public void setStudentNotPipeApproved(String studentNotPipeApproved) {
        this.studentNotPipeApproved = studentNotPipeApproved;
    }

    public List<String> getApprovedStates() {
        return approvedStates;
    }

    public void setApprovedStates(List<String> approvedStates) {
        this.approvedStates = approvedStates;
    }
}
