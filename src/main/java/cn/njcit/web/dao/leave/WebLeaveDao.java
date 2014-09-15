package cn.njcit.web.dao.leave;

import cn.njcit.web.controller.leave.LeaveCheckForm;
import cn.njcit.web.controller.leave.LeaveItem;
import cn.njcit.web.controller.leave.leaveQueryForm;

import java.util.List;

/**
 * Created by YK on 2014/9/12.
 */
public interface WebLeaveDao {

    List<LeaveItem> queryLeave(leaveQueryForm webQueryForm);

    int queryLeaveCount(leaveQueryForm webQueryForm);

    int checkLeave(LeaveCheckForm leaveItem);
}
