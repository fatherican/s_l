package cn.njcit.domain.leave;

import java.io.Serializable;

/**
 * Created by YK on 2014/7/26.
 */
public class Leave implements Serializable {
    private String         leaveId;
    private String         studentId;
    private String      studentName;
    private String         classId;
    private String         professionalId;
    private String         colleageId;
    private String         needSecondApprove;
    private String         instructorApproved;
    private String         studentPipeApproved;
    private String         approved;
    private String      instructorNote;
    private String      studentPipeNote;
    private String         leaveType;
    private String         courseIndex;
    private String      teacherName;
    private String      studentMobile;
    private String      courseName;
    private String         leaveDays;
    private String      studentNote;
    private String      leaveStartDate;
    private String      leaveEndDate;
    private String      createTime;
    private String         leaveSicked;
    private String      leaveSickDate;
    private String      leaveDate;

    public Leave() {
    }

    public String getLeaveId() {
        if(leaveId==null) return "";
        return leaveId;
    }

    public void setLeaveId(String leaveId) {
        this.leaveId = leaveId;
    }

    public String getStudentId() {
        if(studentId==null) return "";
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        if(studentName==null) return "";
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassId() {
        if(classId==null) return "";
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getProfessionalId() {
        if(professionalId==null) return "";
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public String getColleageId() {
        if(colleageId==null) return "";
        return colleageId;
    }

    public void setColleageId(String colleageId) {
        this.colleageId = colleageId;
    }

    public String getNeedSecondApprove() {
        if(needSecondApprove==null) return "";
        return needSecondApprove;
    }

    public void setNeedSecondApprove(String needSecondApprove) {
        this.needSecondApprove = needSecondApprove;
    }

    public String getInstructorApproved() {
        if(instructorApproved==null) return "";
        return instructorApproved;
    }

    public void setInstructorApproved(String instructorApproved) {
        this.instructorApproved = instructorApproved;
    }

    public String getStudentPipeApproved() {
        if(studentPipeApproved==null) return "";
        return studentPipeApproved;
    }

    public void setStudentPipeApproved(String studentPipeApproved) {
        this.studentPipeApproved = studentPipeApproved;
    }

    public String getApproved() {
        if(approved==null) return "";
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getInstructorNote() {
        if(instructorNote==null) return "";
        return instructorNote;
    }

    public void setInstructorNote(String instructorNote) {
        this.instructorNote = instructorNote;
    }

    public String getStudentPipeNote() {
        if(studentPipeNote==null) return "";
        return studentPipeNote;
    }

    public void setStudentPipeNote(String studentPipeNote) {
        this.studentPipeNote = studentPipeNote;
    }

    public String getLeaveType() {
        if(leaveType==null) return "";
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getCourseIndex() {
        if(courseIndex==null) return "";
        return courseIndex;
    }

    public void setCourseIndex(String courseIndex) {
        this.courseIndex = courseIndex;
    }

    public String getTeacherName() {
        if(teacherName==null) return "";
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getStudentMobile() {
        if(studentMobile==null) return "";
        return studentMobile;
    }

    public void setStudentMobile(String studentMobile) {
        this.studentMobile = studentMobile;
    }

    public String getCourseName() {
        if(courseName==null) return "";
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLeaveDays() {
        if(leaveDays==null) return "";
        return leaveDays;
    }

    public void setLeaveDays(String leaveDays) {
        this.leaveDays = leaveDays;
    }

    public String getStudentNote() {
        if(studentNote==null) return "";
        return studentNote;
    }

    public void setStudentNote(String studentNote) {
        this.studentNote = studentNote;
    }

    public String getLeaveStartDate() {
        if(leaveStartDate==null) return "";
        return leaveStartDate;
    }

    public void setLeaveStartDate(String leaveStartDate) {
        this.leaveStartDate = leaveStartDate;
    }

    public String getLeaveEndDate() {
        if(leaveEndDate==null) return "";
        return leaveEndDate;
    }

    public void setLeaveEndDate(String leaveEndDate) {
        this.leaveEndDate = leaveEndDate;
    }

    public String getCreateTime() {
        if(createTime==null) return "";
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLeaveSicked() {
        if(leaveSicked==null) return "";
        return leaveSicked;
    }

    public void setLeaveSicked(String leaveSicked) {
        this.leaveSicked = leaveSicked;
    }

    public String getLeaveSickDate() {
        if(leaveSickDate==null) return "";
        return leaveSickDate;
    }

    public void setLeaveSickDate(String leaveSickDate) {
        this.leaveSickDate = leaveSickDate;
    }

    public String getLeaveDate() {
        if(leaveDate==null) return "";
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }
}
