package cn.njcit.web.controller.user;

import cn.njcit.common.util.CommonUtil;
import cn.njcit.common.util.encrypt.MD5Util;
import cn.njcit.domain.user.User;
import cn.njcit.web.service.user.WebUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by YK on 2014/9/11.
 */
@Controller
@RequestMapping("webUser")
public class WebUserController {

    @Autowired
    private WebUserService webUserService;

    /**
     *登出界面跳转
     * @param request
     * @return
     */
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request,HttpServletResponse  response){
        HttpSession session = request.getSession();
        if(session!=null){
            session.invalidate();
        }
        return "redirect:/webUser/loginPage.do";
    }


    /**
     * 登陆界面跳转
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("loginPage")
    public String loginPage(HttpServletRequest request,HttpServletResponse response){
        return "/web/login";
    }

    /**
     * 登陆
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public @ResponseBody
    Map login(UserQueryForm userForm,HttpServletRequest request,HttpServletResponse response){
        User user = webUserService.login(userForm);
        if(user!=null){
            request.getSession().setAttribute("user",user);
            return CommonUtil.ajaxSuccess(user);
        }
        return CommonUtil.ajaxFail(null,"用户名或密码错误");
    }


    /**
     * 修改密码
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "updatePassword",method = RequestMethod.POST)
    public @ResponseBody
    Map updatePassword(UserQueryForm userForm,HttpServletRequest request,HttpServletResponse response){
        User sessionUser = (User) request.getSession().getAttribute("user");
        if(sessionUser!=null){
            String sessionPassword = sessionUser.getPassword();//缓存中的密码
            String oldPassword = request.getParameter("oldPassword");
            if(!sessionPassword.equals(MD5Util.md5Hex(oldPassword))){
                return CommonUtil.ajaxFail(null,"原密码不正确！！！");
            }
            String confirmPassword = request.getParameter("confirmPassword");
            if(StringUtils.isNotEmpty(confirmPassword) && StringUtils.isNotEmpty(userForm.getPassword())){
                if(!userForm.getPassword().equals(confirmPassword)){
                    return CommonUtil.ajaxFail(null,"新密码和确认密码不一致！！！");
                }
                if(sessionPassword.equals(MD5Util.md5Hex(userForm.getPassword()))){//缓存中的密码和要修改的密码一直，那么不去更新数据库
                    return CommonUtil.ajaxSuccess(true);
                }

                userForm.setUserId(sessionUser.getUserId());
                userForm.setPassword(MD5Util.md5Hex(userForm.getPassword()));//将该密码修改为加密后的密码
                int count = webUserService.updatePassword(userForm);
                if(count>0){
                    sessionUser.setPassword(userForm.getPassword());//更新缓存中的密码为新密码
                    return CommonUtil.ajaxSuccess(true);
                }
            }else{
                return CommonUtil.ajaxFail(null,"新密码或确认密码为空！！！");
            }


        }
        return CommonUtil.ajaxFail(null,"请刷新界面");
    }




}
