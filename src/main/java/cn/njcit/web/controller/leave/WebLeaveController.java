package cn.njcit.web.controller.leave;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by YK on 2014/9/11.
 */
@Controller
@RequestMapping("webLeave")
public class WebLeaveController {

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(HttpServletRequest request,HttpServletResponse response){
        return "/web/index";
    }


}
