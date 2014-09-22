package cn.njcit.dao.leave;

import java.util.List;
import java.util.Map;

import cn.njcit.controller.leave.LeaveStatisticsQueryForm;
import cn.njcit.domain.leave.*;
/**
 * Created by YK on 2014-06-30.
 */
public interface LeaveDao {
    /**
     * 插入请假记录
     * @param reqMap
     * @return
     */
    int addLeave(Map reqMap);

    /**
     * 查询学生请假列表
     * @param reqMap
     * @return
     */
    List<Leave> queryLeaveList(Map reqMap);

    /**
     * 根据leaveId 获得请假条目
     * @param leaveId
     * @return
     */
    Leave getUniqueLeave(String leaveId);

    /**
     * 删除请假条目
     * @param reqMap
     * @return
     */
    int delLeaveItem(Map reqMap);

    /**
     * 更新请假状态
     * @param reqMap
     * @return
     */
    int updateLeaveApprovedState(Map reqMap);

    /**
     * 学生销假
     * @param reqMap
     * @return
     */
    int sickLeave(Map reqMap);

    /**
     * 查看请假列表，可以根据班级查询，也可以按照时间查询
     * @param reqMap
     * @return
     */
    List<Leave> getLeaveList(Map reqMap);

    /**
     * 获得销假列表，已销假 或 为销假
     * @param reqMap
     * @return
     */
    List<Leave> getSickLeaveList(Map reqMap);

    int statisticsLeaveDays(Map queryMap);

    int statisticsLeaveCourseTimes(Map queryMap);

    List<Map> teacherGetLeaveStatistics(LeaveStatisticsQueryForm leaveQueryForm);

}
