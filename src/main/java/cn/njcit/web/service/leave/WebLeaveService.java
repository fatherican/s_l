package cn.njcit.web.service.leave;

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
    List<LeaveItem> queryLeave(leaveQueryForm webQueryForm);
}
