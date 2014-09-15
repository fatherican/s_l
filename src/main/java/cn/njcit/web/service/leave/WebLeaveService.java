package cn.njcit.web.service.leave;

import cn.njcit.domain.user.User;
import cn.njcit.web.controller.leave.LeaveCheckForm;
import cn.njcit.web.controller.leave.LeaveItem;
import cn.njcit.web.controller.leave.leaveQueryForm;

import java.util.List;

/**
 * Created by YK on 2014/9/12.
 */
public interface WebLeaveService {

    /**
     * 查询学生请假列表
     * @param webQueryForm
     * @return
     */
    List<LeaveItem> queryLeave(leaveQueryForm webQueryForm,User user);

    /**
     * 查询为审批的学生请假列表
     * @param webQueryForm
     * @param user
     * @return
     */
    List<LeaveItem> queryUnCheckedLeave(leaveQueryForm webQueryForm, User user);
    /**
     * 查询为审批的学生请假列表 的个数
     * @param webQueryForm
     * @param user
     * @return
     */
    int queryUnCheckedLeaveCount(leaveQueryForm webQueryForm, User user);

    /**
     * 审批请假
     * @param leaveItem
     * @param user
     * @return
     */
    int checkLeave(LeaveCheckForm leaveItem, User user);

    List<LeaveItem> queryCheckedLeave(leaveQueryForm webQueryForm, User user);

    int queryCheckedLeaveCount(leaveQueryForm webQueryForm, User user);
}
