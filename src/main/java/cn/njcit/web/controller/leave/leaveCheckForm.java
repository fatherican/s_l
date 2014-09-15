package cn.njcit.web.controller.leave;

import cn.njcit.web.domain.DataTableForm;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by YK on 2014/9/12.
 */
public class LeaveCheckForm extends DataTableForm{
    private String leaveId;
    private String approved;//0 不同意 1同意
    private String note;//审批意见
    private int role;//角色 1;//辅导员角色 2;//学管处角色 3;//超级管理员角色
    private String finalApproved;//-1 未审批0 未通过 1通过 2辅导员已审批等待学管处审批)
    private Date approvedDate;
    private String userId;
    public String getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(String leaveId) {
        this.leaveId = leaveId;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getFinalApproved() {
        return finalApproved;
    }

    public void setFinalApproved(String finalApproved) {
        this.finalApproved = finalApproved;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
