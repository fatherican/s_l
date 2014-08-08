package cn.njcit.service.leave;

import cn.njcit.common.exception.ServiceException;
import cn.njcit.domain.leave.Leave;
import org.codehaus.jackson.JsonParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by YK on 2014-06-30.
 */
public interface LeaveService {

    /**
     * 添加请假记录
     * @param reqMap
     * @return
     */
    int addLeave(Map reqMap) throws ServiceException;

    /**
     *reqMap中存放字段：
     * ①studentId   ②approved (是否已审核的标记) ③createTime  (createTimeStart  , createTimeEnd)
     *④pageNum(页码) ⑤pageSize(每页显示条数)
     * ⑥approvedStates (审批状态) ，⑦leaveSicked 销假状态(0 未 销假 1 已销假)
     * @param reqMap
     */
    List<Leave> queryLeaveList(Map reqMap);

    /**
     * 删除一个请假条目
     * ①leaveId ②userId
     * @param reqMap
     * @return
     */
    int delLeaveItem(Map reqMap) throws ServiceException;

    /**
     * 更新 请假条  的状态
     *
     * ①userId
     * ②leaveId
     * ③note
     * @param reqMap
     * @return
     */
    int updateLeaveApprovedState(Map reqMap);

    /**
     * 学生销假
     * ①leaveId  ②userId
     * @param reqMap
     * @return
     */
    int sickLeave(Map reqMap) throws ServiceException;

    /**
     * 查询请假列表
     * ①userId ②pageNum ③pageSize
     * ④startTime ⑤endTime ⑥classId
     * ⑦leaveSicked
     * @param reqMap
     * @return
     */
    List<Leave> getLeaveList(Map reqMap);

    /**
     * 获得销假列表,该方法间接调用 getLeaveList
     * ①viewType(1 按照时间查看 2按照班级查看) ②sicked(0 未销假  1 已销假)
     * @param reqMap
     * @return
     */
    List<Leave> getSickedLeaveList(Map reqMap);

    /**
     * 老师获得学生的请假列表
     * ①viewType:1 是按时间查看 2是按照班级查看
     * ①userId ②pageNum ③pageSize
     * ④startTime ⑤endTime ⑥classId
     * ⑦leaveSicked
     * @param reqMap
     * @return
     */
    List<Leave> getTeacherMangagedLeaveList(Map reqMap);
}
