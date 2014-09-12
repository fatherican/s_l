package cn.njcit.web.controller.leave;

import cn.njcit.common.util.CommonUtil;
import cn.njcit.web.service.leave.WebLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
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
        return "/web/index";
    }


    @RequestMapping(value = "unCheckedLeaveList",method = RequestMethod.POST)
    public @ResponseBody Map unCheckedLeaveList(leaveQueryForm webQueryForm,HttpServletRequest request,HttpServletResponse response){
        //初始化datatable
        webQueryForm.initDataTable(request);
        List<LeaveItem> leaveItemList  = webLeaveService.queryLeave(webQueryForm);


        return CommonUtil.reurnDataTable(10, "a", "");
    }


}
