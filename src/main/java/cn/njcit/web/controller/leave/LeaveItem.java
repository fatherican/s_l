package cn.njcit.web.controller.leave;

import java.util.Date;

/**
 * Created by YK on 2014-09-12.
 */
public class LeaveItem {
    private String leaveId;
    private String studentId;
    private String userNo;//学号
    private String studentName;
    private String className;
    private String colleageName;
    private String leaveType;//（0 节次请假，1天数请假）
    private Date leaveStartDate;
    private Date leaveEndDate;
    private String leaveDays;
    private String courseIndex;
    private String createTime;
    private String studentNote;
    private String instructorNote;//辅导员审批意见
    private String studentPipeNote;//学管处意见
    private String approved;//总审批状态(-1 未审批0 未通过 1通过 2辅导员已审批等待学管处审批)

    private String colleageId;
    private String needSecondApprove;//（0 不需要 1 需要）
    private Date instructorApprovedDate;//辅导员审批时间
    private String instructorId;//审批假条辅导员Id
    private String instructorApproved;//(-1 未审批0 未通过 1通过)
    private Date studentPipeApprovedDate;//学管处审批时间
    private String studentPipeId;//学管处审批人ID
    private String studentPipeApproved;//(-1 未审批0 未通过 1通过)
    private String teacherName;
    private String studentMobile;
    private String courseName;
    private String leaveSicked ;//（0未销假 1 已销假）
    private String leaveSickDate;

    public LeaveItem() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public Date getLeaveStartDate() {
        return leaveStartDate;
    }

    public void setLeaveStartDate(Date leaveStartDate) {
        this.leaveStartDate = leaveStartDate;
    }

    public Date getLeaveEndDate() {
        return leaveEndDate;
    }

    public void setLeaveEndDate(Date leaveEndDate) {
        this.leaveEndDate = leaveEndDate;
    }

    public String getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(String leaveDays) {
        this.leaveDays = leaveDays;
    }

    public String getCourseIndex() {
        return courseIndex;
    }

    public void setCourseIndex(String courseIndex) {
        this.courseIndex = courseIndex;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStudentNote() {
        return studentNote;
    }

    public void setStudentNote(String studentNote) {
        this.studentNote = studentNote;
    }

    public String getInstructorNote() {
        return instructorNote;
    }

    public void setInstructorNote(String instructorNote) {
        this.instructorNote = instructorNote;
    }

    public String getStudentPipeNote() {
        return studentPipeNote;
    }

    public void setStudentPipeNote(String studentPipeNote) {
        this.studentPipeNote = studentPipeNote;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getColleageId() {
        return colleageId;
    }

    public void setColleageId(String colleageId) {
        this.colleageId = colleageId;
    }

    public String getNeedSecondApprove() {
        return needSecondApprove;
    }

    public void setNeedSecondApprove(String needSecondApprove) {
        this.needSecondApprove = needSecondApprove;
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getStudentMobile() {
        return studentMobile;
    }

    public void setStudentMobile(String studentMobile) {
        this.studentMobile = studentMobile;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLeaveSicked() {
        return leaveSicked;
    }

    public void setLeaveSicked(String leaveSicked) {
        this.leaveSicked = leaveSicked;
    }

    public String getLeaveSickDate() {
        return leaveSickDate;
    }

    public void setLeaveSickDate(String leaveSickDate) {
        this.leaveSickDate = leaveSickDate;
    }

    public String getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(String leaveId) {
        this.leaveId = leaveId;
    }

    public Date getInstructorApprovedDate() {
        return instructorApprovedDate;
    }

    public void setInstructorApprovedDate(Date instructorApprovedDate) {
        this.instructorApprovedDate = instructorApprovedDate;
    }

    public Date getStudentPipeApprovedDate() {
        return studentPipeApprovedDate;
    }

    public void setStudentPipeApprovedDate(Date studentPipeApprovedDate) {
        this.studentPipeApprovedDate = studentPipeApprovedDate;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public String getStudentPipeId() {
        return studentPipeId;
    }

    public void setStudentPipeId(String studentPipeId) {
        this.studentPipeId = studentPipeId;
    }

}
