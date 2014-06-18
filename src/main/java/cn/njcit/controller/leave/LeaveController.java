package cn.njcit.controller.leave;

import cn.njcit.common.constants.AppConstants;
import cn.njcit.common.util.CommonUtil;
import cn.njcit.common.util.UID;
import cn.njcit.common.util.encrypt.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YK on 2014-06-18.
 */
@Controller
@RequestMapping("/leave")
public class LeaveController {


    /**
     * 学生申请请假
     * 1、按课程请假
     * 2、按天数请假
     * @param request
     * @return
     */
    @RequestMapping(value = "addLeave", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String addLeave(HttpServletRequest request){
        boolean isError = false;//传递参数是否出错
        StringBuffer errorMessage = new StringBuffer();

        Map reqMap = new HashMap();
        String userId = request.getParameter("userId");
        String leaveType = request.getParameter("leaveType");//请假类型
        String token = request.getParameter("token");
        String key = AppConstants.appConfig.getProperty("app.key");
        String neededToken = MD5Util.md5Hex(userId + key);
        if(StringUtils.isEmpty(leaveType)){
            isError=true;
            errorMessage.append("请假类型为空\t");
            return  CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }
        if(!neededToken.equals(token)){
            isError=true;
            errorMessage.append("不合法用户\t");
        }
        String studentNote = request.getParameter("student_note");//学生的备注信息

        if(AppConstants.LEAVE_CLASS==Integer.parseInt(leaveType)){//课程请假
            reqMap.put("leaveType",leaveType);
            String courseIndex = request.getParameter("courseIndex");//所请假的
            reqMap.put("courseIndex",courseIndex);
            String teacherName = request.getParameter("teacher_name");
            reqMap.put("teacherName",teacherName);
            String studentMobile = request.getParameter("student_mobile");
            reqMap.put("studentMobile",studentMobile);
            String courseName = request.getParameter("course_name");//课程名
            reqMap.put("courseName",courseName);

        }else if(AppConstants.LEAVE_DAY==Integer.parseInt(leaveType)){//天数请假




        }else{
            errorMessage.append("请假类型不匹配\t");
            return  CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", errorMessage.toString());
        }





        return null;
    }




    /**
     * 学生获得请假列表。
     * 包括：
     * 1：待审批
     * 2：最新审批（最近一周的审批结果，包括已审批和未审批）
     * 3：审批列表时间段查询
     * @param request
     * @return
     */
    @RequestMapping(value = "studentGetLeaveList", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String getLeaveList(HttpServletRequest request){
        return null;
    }


    /**
     * 学生删除请假，只允许删除尚未审批的请假。
     * @param request
     * @return
     */
    @RequestMapping(value = "delLeaveItem", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String delLeaveItem(HttpServletRequest request){
        return null;
    }

     /**
     *辅导员审批
     * @param request
     * @return
     */
     @RequestMapping(value = "instructorApproval", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String instructorApproval(HttpServletRequest request){
        return null;
    }

    /**
     *学管处审批
     * @param request
     * @return
     */
    @RequestMapping(value ="studentPipeApproval", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String studentPipeApproval(HttpServletRequest request){
        return null;
    }


    /**
     *学生 销假
     * @param request
     * @return
     */
    @RequestMapping(value ="studentSickLeave", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String studentSickLeave(HttpServletRequest request){
        return null;
    }

    /**
     *老师查看请假列表（包括，辅导员和学管处）
     *包括：
     * 1、按时间排序
     * 2、按班级查看
     * @param request
     * @return
     */
    @RequestMapping(value ="teacherGetLeaveList", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String teacherGetLeaveList(HttpServletRequest request){
        return null;
    }

    /**
     *获得该老师负责的班级
     * @param request
     * @return
     */
    @RequestMapping(value ="getTeacherManagedClass", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String getTeacherManagedClass(HttpServletRequest request){
        return null;
    }



    /**
     *老师获得学生的销假列表
     * @param request
     * @return
     */
    @RequestMapping(value ="teacherGetStudentSickedLeaveList", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String teacherGetStudentSickedLeaveList(HttpServletRequest request){
        return null;
    }






}
