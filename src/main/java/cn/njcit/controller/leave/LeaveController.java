package cn.njcit.controller.leave;

import cn.njcit.common.constants.AppConstants;
import cn.njcit.common.exception.ServiceException;
import cn.njcit.common.util.CommonUtil;
import cn.njcit.common.util.encrypt.MD5Util;
import cn.njcit.domain.leave.Leave;
import cn.njcit.service.leave.LeaveService;
import cn.njcit.service.user.UserService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YK on 2014-06-18.
 */
@Controller
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private UserService userService;


    /**
     * 学生申请请假
     * 1、按课程请假
     * 2、按天数请假
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addLeave", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String addLeave(HttpServletRequest request) throws ServiceException {
        boolean isError = false;//传递参数是否出错
        StringBuffer errorMessage = new StringBuffer();

        Map reqMap = new HashMap();
        String userId = request.getParameter("userId");
        reqMap.put("userId", userId);
        String leaveType = request.getParameter("leaveType");//请假类型
        reqMap.put("leaveType", leaveType);
        String token = request.getParameter("token");
        String key = AppConstants.appConfig.getProperty("app.key");
        String neededToken = MD5Util.md5Hex(userId + key);
        if (StringUtils.isEmpty(leaveType)) {
            isError = true;
            errorMessage.append("请假类型为空\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (!neededToken.equals(token)) {
            isError = true;
            errorMessage.append("不合法用户\t");
        }
        String studentNote = request.getParameter("studentNote");//学生的备注信息
        reqMap.put("studentNote", studentNote);
        String studentMobile = request.getParameter("studentMobile");
        reqMap.put("studentMobile", studentMobile);
        /*==================课程请假  所需要的的参数**/
        String courseIndex = request.getParameter("courseIndex");//所请假的节次
        String[] teacherNames = request.getParameterValues("teacherName");
        String[] courseNames = request.getParameterValues("courseName");//课程名
        String leaveDate = request.getParameter("leaveDate");//请假日期
        /*==================天数请假类型 所需要的参数==============**/
        String leaveStartDate = request.getParameter("leaveStartDate");//所请假的开始日期
        String leaveEndDate = request.getParameter("leaveEndDate");//所请假的结束日期
        String leaveDays = request.getParameter("leaveDays");//请假天数
        if (AppConstants.LEAVE_CLASS == Integer.parseInt(leaveType)) {//课程请假
            if (StringUtils.isEmpty(courseIndex)) {
                isError = true;
                errorMessage.append("请假节次为空\t");
                return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
            }
            if (StringUtils.isEmpty(courseIndex)) {
                isError = true;
                errorMessage.append("请假课程为空\t");
                return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
            }
            if (StringUtils.isEmpty(leaveDate)) {
                isError = true;
                errorMessage.append("请假日期为空\t");
                return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
            }
            reqMap.put("courseIndex", courseIndex);
            reqMap.put("teacherName", arrayToString(teacherNames));
            reqMap.put("courseName", arrayToString(courseNames));
            reqMap.put("leaveDate", leaveDate);
            //设置开始和结束日期都为请假日期当天
            reqMap.put("leaveStartDate", leaveDate);
            reqMap.put("leaveEndDate", leaveDate);
        } else if (AppConstants.LEAVE_DAY == Integer.parseInt(leaveType)) {//天数请假
            if (StringUtils.isEmpty(leaveStartDate)) {
                isError = true;
                errorMessage.append("请假开始日期为空\t");
                return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
            }
            if (StringUtils.isEmpty(leaveEndDate)) {
                isError = true;
                errorMessage.append("请假结束日期为空\t");
                return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
            }
            if (StringUtils.isEmpty(leaveDays)) {
                isError = true;
                errorMessage.append("请假天数为空\t");
                return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
            }
            reqMap.put("leaveStartDate", leaveStartDate);
            reqMap.put("leaveDate", leaveStartDate);
            reqMap.put("leaveEndDate", leaveEndDate);
            reqMap.put("leaveDays", leaveDays);
        } else {
            errorMessage.append("请假类型不匹配\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }

        int leaveId = leaveService.addLeave(reqMap);

        return CommonUtil.ajaxReturn(AppConstants.SUCCESS, leaveId + "", "请假success");
    }


    /**
     * 学生获得请假列表。
     * 包括：
     * 1：待审批
     * 2：最新审批（最近一周的审批结果，包括已审批和未审批）
     * 3：审批列表时间段查询
     * 4:获得已审批未销假列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "studentGetLeaveList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getLeaveList(HttpServletRequest request) throws ParseException {
        StringBuffer errorMessage = new StringBuffer();
        Map reqMap = new HashMap();
        String requestType = request.getParameter("studentQueryLeaveListType");//请求的列表数据类型  1://待审批   2://最新审批 3://审批列表时间段查询
        String userId = request.getParameter("userId");
        reqMap.put("userId", userId);
        reqMap.put("studentId", userId);
        String token = request.getParameter("token");
        String key = AppConstants.appConfig.getProperty("app.key");
        String neededToken = MD5Util.md5Hex(userId + key);

        if (!neededToken.equals(token)) {
            errorMessage.append("不合法用户\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(requestType)) {
            errorMessage.append("请求列表数据类型为空\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }

        String startTime = request.getParameter("startTime");//格式yyyy-MM-dd hh:mm:ss
        String endTime = request.getParameter("endTime");//格式yyyy-MM-dd hh:mm:ss
        String pageNum = request.getParameter("pageNum");//页码
        String pageSize = request.getParameter("pageSize");//每页显示的条数
        if (StringUtils.isEmpty(pageNum)) {
            errorMessage.append("页码不允许为空\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = "20";
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        reqMap.put("pageNum", pageNum);
        reqMap.put("pageSize", pageSize);
        int requestTypeNum = Integer.parseInt(requestType);
        List<Leave> queryResultList = null;
        switch (requestTypeNum) {
            case 1://待审批
                reqMap.put("approvedStates", new String[]{"-1", "2"});//-1 未审批  , 2辅导员已审批等待学管处审批
                reqMap.put("studentId", userId);
                queryResultList = leaveService.queryLeaveList(reqMap);
                break;
            case 2://最新审批（最近一周的审批结果，包括已审批和未审批）
                reqMap.put("studentId", userId);
                reqMap.put("createTimeStart", DateFormatUtils.format(DateUtils.addWeeks(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
                reqMap.put("createTimeEnd", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                queryResultList = leaveService.queryLeaveList(reqMap);
                break;
            case 3://审批列表时间段查询
                reqMap.put("studentId", userId);
                if (StringUtils.isEmpty(startTime)) {
                    errorMessage.append("开始时间不允许为空\t");
                    return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
                }
                if (StringUtils.isEmpty(endTime)) {
                    errorMessage.append("结束时间不允许为空\t");
                    return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
                }
                reqMap.put("createTimeStart", DateFormatUtils.format(DateUtils.addWeeks(DateUtils.parseDate(startTime, new String[]{"yyyy-MM-dd HH:mm:ss"}), -1), "yyyy-MM-dd HH:mm:ss"));
                reqMap.put("createTimeEnd", DateFormatUtils.format(DateUtils.parseDate(endTime, new String[]{"yyyy-MM-dd HH:mm:ss"}), "yyyy-MM-dd HH:mm:ss"));
                queryResultList = leaveService.queryLeaveList(reqMap);
                break;
            case 4://获得已审批未销假列表
                reqMap.put("approved", "1");//已审批
                reqMap.put("leaveSicked", "0");//未销假
                reqMap.put("studentId", userId);
                reqMap.put("leaveEndDate", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                queryResultList = leaveService.queryLeaveList(reqMap);
                break;
        }

        return CommonUtil.ajaxReturn(AppConstants.SUCCESS, JSON.toJSONString(queryResultList), "查询success");
    }


    /**
     * 学生删除请假，只允许删除尚未审批的请假。
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "delLeaveItem", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String delLeaveItem(HttpServletRequest request) {
        Map reqMap = new HashMap();
        StringBuffer errorMessage = new StringBuffer();
        String userId = request.getParameter("userId");
        String leaveId = request.getParameter("leaveId");
        reqMap.put("userId", userId);
        reqMap.put("leaveId", leaveId);
        String token = request.getParameter("token");
        String key = AppConstants.appConfig.getProperty("app.key");
        String neededToken = MD5Util.md5Hex(userId + key);
        if (!neededToken.equals(token)) {
            errorMessage.append("不合法用户\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(leaveId)) {
            errorMessage.append("请假条目ID为空\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        int count = 0;
        try {
            count = leaveService.delLeaveItem(reqMap);
        } catch (ServiceException e) {
            e.printStackTrace();
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", e.getMessage());
        }
        return CommonUtil.ajaxReturn(AppConstants.SUCCESS, leaveId, "请假条目已经删除");
    }

    /**
     * 辅导员或学管处审批
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "instructorApproval", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String instructorApproval(HttpServletRequest request) {
        Map reqMap = new HashMap();
        StringBuffer errorMessage = new StringBuffer();
        String userId = request.getParameter("userId");
        String leaveId = request.getParameter("leaveId");
        String note = request.getParameter("note");//备注信息
        reqMap.put("userId", userId);
        reqMap.put("leaveId", leaveId);
        reqMap.put("note", note);
        String token = request.getParameter("token");
        String key = AppConstants.appConfig.getProperty("app.key");
        String neededToken = MD5Util.md5Hex(userId + key);
        if (!neededToken.equals(token)) {
            errorMessage.append("不合法用户\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(userId)) {
            errorMessage.append("用户编号为空\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(leaveId)) {
            errorMessage.append("请假条目ID为空\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        int count = leaveService.updateLeaveApprovedState(reqMap);
        if (count > 0) {
            return CommonUtil.ajaxReturn(AppConstants.SUCCESS, "", "success");
        }
        return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", "fail");
    }
/*
    *//**
     *学管处审批
     * @param request
     * @return
     *//*
    @RequestMapping(value ="studentPipeApproval", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String studentPipeApproval(HttpServletRequest request){
        Map reqMap = new HashMap();
        StringBuffer errorMessage = new StringBuffer();
        String userId = request.getParameter("userId");
        String leaveId = request.getParameter("leaveId");
        String note = request.getParameter("note");//备注信息
        reqMap.put("userId",userId);
        reqMap.put("leaveId",leaveId);
        reqMap.put("note",note);
        String token = request.getParameter("token");
        String key = AppConstants.appConfig.getProperty("app.key");
        String neededToken = MD5Util.md5Hex(userId + key);
        if(!neededToken.equals(token)){
            errorMessage.append("不合法用户\t");
            return  CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if(StringUtils.isEmpty(leaveId)){
            errorMessage.append("请假条目ID为空\t");
            return  CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        int count = leaveService.updateLeaveApprovedState(reqMap);
        if(count>0){
            return  CommonUtil.ajaxReturn(AppConstants.SUCCESS, "","success");
        }
        return  CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "","fail");
    }*/


    /**
     * 学生 销假
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "studentSickLeave", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String studentSickLeave(HttpServletRequest request) throws ServiceException {
        Map reqMap = new HashMap();
        StringBuffer errorMessage = new StringBuffer();
        String userId = request.getParameter("userId");
        String leaveId = request.getParameter("leaveId");
        reqMap.put("userId", userId);
        reqMap.put("leaveId", leaveId);
        String token = request.getParameter("token");
        String key = AppConstants.appConfig.getProperty("app.key");
        String neededToken = MD5Util.md5Hex(userId + key);
        if (!neededToken.equals(token)) {
            errorMessage.append("不合法用户\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(userId)) {
            errorMessage.append("用户编号为空\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(leaveId)) {
            errorMessage.append("请假条目ID为空\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        int count = leaveService.sickLeave(reqMap);
        if (count > 0) {
            return CommonUtil.ajaxReturn(AppConstants.SUCCESS, "", "success");
        }
        return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", "fail");
    }


    /**
     * 获得该老师(辅导员和学管处)负责的班级
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getTeacherManagedClass", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getTeacherManagedClass(HttpServletRequest request) {
        Map reqMap = new HashMap();
        StringBuffer errorMessage = new StringBuffer();
        String userId = request.getParameter("userId");
        reqMap.put("userId", userId);
        String token = request.getParameter("token");
        String key = AppConstants.appConfig.getProperty("app.key");
        String neededToken = MD5Util.md5Hex(userId + key);
        if (!neededToken.equals(token)) {
            errorMessage.append("不合法用户\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(userId)) {
            errorMessage.append("用户编号为空\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        List<Map> classes = userService.getTeacherManagedClass(reqMap);
        return CommonUtil.ajaxReturn(AppConstants.SUCCESS, classes, "success");
    }

    /**
     * 老师查看请假列表（包括，辅导员和学管处）
     * 包括：
     * 1、按时间排序
     * 2、按班级查看
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "teacherGetLeaveList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String teacherGetLeaveList(HttpServletRequest request) {
        Map reqMap = new HashMap();
        StringBuffer errorMessage = new StringBuffer();
        String viewType = request.getParameter("viewType");//请假列表查看类型，1 是按时间查看 2是按照班级查看
        reqMap.put("viewType", viewType);
        String userId = request.getParameter("userId");
        reqMap.put("userId", userId);
        String pageNum = request.getParameter("pageNum");//页码
        reqMap.put("pageNum", pageNum);
        String pageSize = request.getParameter("pageSize");//每页显示的条目
        reqMap.put("pageSize", pageSize);
        /*=========根据班级类型来查看请假列表==============*/
        String classId = request.getParameter("classId");
        reqMap.put("classId", classId);
         /*=========根据时间段来查看请假列表==============*/
        String startTime = request.getParameter("startTime");
        reqMap.put("startTime", startTime);
        String endTime = request.getParameter("endTime");
        reqMap.put("endTime", endTime);

        String token = request.getParameter("token");
        String key = AppConstants.appConfig.getProperty("app.key");
        String neededToken = MD5Util.md5Hex(userId + key);
        if (!neededToken.equals(token)) {
            errorMessage.append("不合法用户\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(userId)) {
            errorMessage.append("用户编号为空\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(viewType)) {
            errorMessage.append("列表查看类型为空，1 是按时间查看 2是按照班级查看\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(pageNum) || !pageNum.matches("\\d*") || Integer.parseInt(pageNum) < 0) {
            errorMessage.append("页码不允许为空且必须大于0\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(pageSize) || !pageSize.matches("\\d*") || Integer.parseInt(pageSize) < 0) {
            errorMessage.append("每页显示条目数不允许为空且必须大于0\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }

        if ("1".equals(viewType)) {//按照时间查看
            if (StringUtils.isEmpty(startTime)) {
                errorMessage.append("开始时间不允许为空\t");
                return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
            }
            if (StringUtils.isEmpty(endTime)) {
                errorMessage.append("结束时间不允许为空\t");
                return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
            }
        } else if ("2".equals(viewType)) {//按班级查看
            if (StringUtils.isEmpty(classId)) {
                errorMessage.append("班级不允许为空\t");
                return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
            }
        }
        List<Leave> leaveList = leaveService.getTeacherMangagedLeaveList(reqMap);
        return CommonUtil.ajaxReturn(AppConstants.SUCCESS, JSON.toJSONString(leaveList), "成功");
    }


    /**
     * 老师（包括，辅导员和学管处）获得学生的销假列表
     * 1:按照班级来查看
     * 2：按照时间来查看
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "teacherGetStudentSickedLeaveList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String teacherGetStudentSickedLeaveList(HttpServletRequest request) throws ParseException {
        Map reqMap = new HashMap();
        StringBuffer errorMessage = new StringBuffer();
        String userId = request.getParameter("userId");
        reqMap.put("userId", userId);
        String pageNum = request.getParameter("pageNum");//页码
        reqMap.put("pageNum", pageNum);
        String pageSize = request.getParameter("pageSize");//每页显示的条目
        reqMap.put("pageSize", pageSize);

        String token = request.getParameter("token");
        String key = AppConstants.appConfig.getProperty("app.key");
        String neededToken = MD5Util.md5Hex(userId + key);
        if (!neededToken.equals(token)) {
            errorMessage.append("不合法用户\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(userId)) {
            errorMessage.append("用户编号为空\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(pageNum) || !pageNum.matches("\\d*") || Integer.parseInt(pageNum) < 0) {
            errorMessage.append("页码不允许为空且必须大于0\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(pageSize) || !pageSize.matches("\\d*") || Integer.parseInt(pageSize) < 0) {
            errorMessage.append("每页显示条目数不允许为空且必须大于0\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }


        String viewType = request.getParameter("viewType");//查看类型 1 按照时间查看 2按照班级查看
        String leaveSicked = request.getParameter("leaveSicked");//销假状态 0 未销假  1 已销假
        if (StringUtils.isEmpty(viewType)) {
            errorMessage.append("查看类型为空0\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if (StringUtils.isEmpty(leaveSicked)) {
            errorMessage.append("销假状态为空0\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        reqMap.put("viewType", viewType);
        reqMap.put("leaveSicked", leaveSicked);
        reqMap.put("currentDate", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        if ("1".equals(leaveSicked)) {//1 已销假 只能查看当前三天销假的数据，按照销假时间倒序排序
            reqMap.put("leaveSickDateStart", DateFormatUtils.format(DateUtils.addDays(new Date(), -3), "yyyy-MM-dd HH:mm:ss"));
            reqMap.put("leaveSickDateEnd", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            reqMap.put("leave_sicked", "1");
        } else if ("0".equals(leaveSicked)) {//未销假，但是时间已经到了销假的时间
            reqMap.put("leave_sicked", 0);
            reqMap.put("leaveEndDate", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        }
        if ("1".equals(viewType)) {//按时间查看并且销假状态为未销假，此时必须要传递startTime  和 endTime
            if ("0".equals(leaveSicked)) {//0 未销假  ,查询从startTime 到 endTime 时间段，并且未销假 的条目，时间，按照请假时间正序排序
                String startTime = request.getParameter("startTime");
                String endTime = request.getParameter("endTime");
                if (StringUtils.isEmpty(startTime)) {
                    errorMessage.append("开始时间不允许为空\t");
                    return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
                }
                if (StringUtils.isEmpty(endTime)) {
                    errorMessage.append("结束时间不允许为空\t");
                    return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
                }
                reqMap.put("createTimeStart", startTime);
                reqMap.put("createTimeEnd", endTime);
            }
        } else {//按班级查看
            String classId = request.getParameter("classId");
            if (StringUtils.isEmpty(classId)) {
                errorMessage.append("开始时间不允许为空\t");
                return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
            }
            reqMap.put("classId", classId);
        }

        List<Leave> leaveList = leaveService.getSickedLeaveList(reqMap);
        return CommonUtil.ajaxReturn(AppConstants.SUCCESS, JSON.toJSONString(leaveList), "成功");
    }


    /**
     * 将字符串数组，转换成 如下格式 “aaaa,bbbb,cccc”
     *
     * @param arrarys
     * @return
     */
    private String arrayToString(String arrarys[]) {
        StringBuffer strs = new StringBuffer();
        if (arrarys != null && arrarys.length > 0) {
            for (int i = 0; i < arrarys.length; i++) {
                if (i == 0) {
                    strs.append(arrarys[i]);
                } else {
                    strs.append("," + arrarys[i]);
                }

            }
        }
        return strs.toString();
    }


    /**
     * （学生）  请假  统计
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "studentLeaveStatistics", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String studentLeaveStatistics(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String token = request.getParameter("token");
        String key = AppConstants.appConfig.getProperty("app.key");
        String neededToken = MD5Util.md5Hex(userId + key);
        if (!neededToken.equals(token)) {
            StringBuffer errorMessage = new StringBuffer();
            errorMessage.append("不合法用户\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if(StringUtils.isEmpty(userId)){
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", "userId 为空");
        }
        String startTime = request.getParameter("startTime");//开始时间
        String endTime = request.getParameter("endTime");
        String statisticsType = request.getParameter("statisticsType");//1  第一节课 2第二节课  4第三节课 8 第四节课 0天（请假类型为天的）
        Map queryMap = new HashMap();
        queryMap.put("startTime", startTime);
        queryMap.put("endTime", endTime);
        queryMap.put("statisticsType", statisticsType);
        queryMap.put("userId",userId);
        Map leavestatisticsMap = leaveService.studentLeaveStatistics(queryMap);
        return CommonUtil.ajaxReturn(200, leavestatisticsMap, null);
    }


    /**
     * （学生）  请假  统计
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "teacherLeaveStatistics", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String teacherLeaveStatistics(LeaveStatisticsQueryForm leaveQueryForm , HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String token = request.getParameter("token");
        String key = AppConstants.appConfig.getProperty("app.key");
        String neededToken = MD5Util.md5Hex(userId + key);
        if (!neededToken.equals(token)) {
            StringBuffer errorMessage = new StringBuffer();
            errorMessage.append("不合法用户\t");
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if(StringUtils.isEmpty(userId)){
            return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", "userId 为空");
        }
        String startTime = request.getParameter("startTime");//开始时间
        String endTime = request.getParameter("endTime");
        String statisticsType = request.getParameter("statisticsType");//1  第一节课 2第二节课  4第三节课 8 第四节课 0天（请假类型为天的）
        Map queryMap = new HashMap();
        queryMap.put("startTime", startTime);
        queryMap.put("endTime", endTime);
        queryMap.put("statisticsType", statisticsType);
        queryMap.put("userId",userId);
        List<Map> leavestatisticsMap = leaveService.teacherGetLeaveStatistics(leaveQueryForm,userId);
        return CommonUtil.ajaxReturn(200, leavestatisticsMap, null);
    }
}