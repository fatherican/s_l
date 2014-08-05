package cn.njcit.controller.user;

import cn.njcit.common.constants.AppConstants;
import cn.njcit.common.exception.ParameterException;
import cn.njcit.common.exception.ServiceException;
import cn.njcit.common.exception.SessionOutException;
import cn.njcit.common.util.CommonUtil;
import cn.njcit.common.util.encrypt.MD5Util;
import cn.njcit.domain.user.User;
import cn.njcit.service.user.UserService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 *操作用户的Controller
 * Created by YK on 2014-06-15.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *用户登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginIn", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String logIn(HttpServletRequest request) throws ServiceException {
        User reqUser = new User();
        String errorMessage = "";
        boolean isError = false;
        String userNo = request.getParameter("userNo");//工号或学号
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String token = request.getParameter("token");
        String requestTime = request.getParameter("requestTime");//当前的系统时间
        String key = AppConstants.appConfig.getProperty("app.key");
        String neededToken = MD5Util.md5Hex(requestTime + key);
        if(StringUtils.isEmpty(userNo)){
            isError=true;
            errorMessage="用户名不允许为空 \t ";
        }
        if(StringUtils.isEmpty(password)){
            isError=true;
            errorMessage+="密码不允许为空 \t ";
        }
        if(StringUtils.isEmpty(role)){
            isError=true;
            errorMessage+="角色不允许为空 \t ";
        }
        if(StringUtils.isEmpty(token)){
            isError=true;
            errorMessage+="非法用户 \t ";
        }
        if(StringUtils.isEmpty(requestTime)){
            isError=true;
            errorMessage+="请求时间为空 \t ";
        }
        if(!neededToken.equals(token)){
            isError=true;
            errorMessage+="非法用户 \t ";
        }

        if(isError){
           return  CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR,"",errorMessage);
        }
        reqUser.setUserNo(userNo);
        password=MD5Util.md5Hex(password);
        reqUser.setPassword(password);
        reqUser.setRole(Integer.parseInt(role));
        try{
            User dbUser = userService.logIn(reqUser);
            if(dbUser!=null){
//                String jsonStr = JSON.toJSONString(dbUser);
                return CommonUtil.ajaxReturn(AppConstants.SUCCESS,dbUser,null);
            }else{
                return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR,"","用户名或密码错误");
            }
        }catch (ParameterException e){
           return CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR,"",e.getMessage());
        }
    }

}
