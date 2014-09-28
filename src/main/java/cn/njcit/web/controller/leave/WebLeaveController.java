package cn.njcit.web.controller.leave;

import cn.njcit.common.util.CommonUtil;
import cn.njcit.domain.user.User;
import cn.njcit.web.service.leave.WebLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by YK on 2014/9/11.
 */
@Controller
@RequestMapping("webLeave")
public class WebLeaveController {

    @Autowired
    private WebLeaveService webLeaveService;

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(HttpServletRequest request,HttpServletResponse response){
        return "/web/leave/index";
    }


    @RequestMapping(value = "unCheckedLeaveList",method = RequestMethod.POST)
    public @ResponseBody Map unCheckedLeaveList(leaveQueryForm webQueryForm,HttpServletRequest request,HttpServletResponse response){
        //初始化datatable
        webQueryForm.initDataTable(request);
        User user = (User) request.getSession().getAttribute("user");
        //设置自己负责的学院  或  班级
        Object managedColleageIdOB = request.getSession().getAttribute("managedColleageId");
        String managedColleageId = managedColleageIdOB==null?null:managedColleageIdOB.toString();
        webQueryForm.setColleageId(managedColleageId);
        Object managedClassesOB = request.getSession().getAttribute("managedClasses");
        List<Map> classesList = managedClassesOB==null?null: (List<Map>) managedClassesOB;
        webQueryForm.setClassesList(classesList);
        //
        List<LeaveItem> leaveItemList  = webLeaveService.queryUnCheckedLeave(webQueryForm, user);
        int totalCount  = webLeaveService.queryUnCheckedLeaveCount(webQueryForm,user);
        return CommonUtil.reurnDataTable(totalCount, leaveItemList, null);
    }


    /**
     * 审批请假
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "checkLeaveItem",method = RequestMethod.POST)
    public @ResponseBody Map checkLeaveItem(LeaveCheckForm leaveCheckForm,HttpServletRequest request,HttpServletResponse response){
        User user = (User)request.getSession().getAttribute("user");
        int  count = webLeaveService.checkLeave(leaveCheckForm,user);
        if(count>0){
            return CommonUtil.ajaxSuccess(true);
        }else{
            return CommonUtil.ajaxFail(null,"审批失败，请联系管理员");
        }
    }

    /**
     * 已审批请假页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "checkedIndex",method = RequestMethod.GET)
    public String checkedIndex(HttpServletRequest request,HttpServletResponse response){
        return "/web/leave/checkedIndex";
    }

    /**
     * 已审批请假列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "checkedLeaveList",method = RequestMethod.POST)
    public @ResponseBody Map checkedLeaveList(leaveQueryForm webQueryForm,HttpServletRequest request,HttpServletResponse response){
        //初始化datatable
        webQueryForm.initDataTable(request);
        User user = (User) request.getSession().getAttribute("user");
        //设置自己负责的学院  或  班级
        Object managedColleageIdOB = request.getSession().getAttribute("managedColleageId");
        String managedColleageId = managedColleageIdOB==null?null:managedColleageIdOB.toString();
        webQueryForm.setColleageId(managedColleageId);
        Object managedClassesOB = request.getSession().getAttribute("managedClasses");
        List<Map> classesList = managedClassesOB==null?null: (List<Map>) managedClassesOB;
        webQueryForm.setClassesList(classesList);
        //
        List<LeaveItem> leaveItemList  = webLeaveService.queryCheckedLeave(webQueryForm, user);
        int totalCount  = webLeaveService.queryCheckedLeaveCount(webQueryForm,user);
        return CommonUtil.reurnDataTable(totalCount, leaveItemList, null);
    }


}
