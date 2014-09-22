package cn.njcit.web.controller.user;

import cn.njcit.common.constants.AppConstants;
import cn.njcit.common.util.CommonUtil;
import cn.njcit.common.util.encrypt.MD5Util;
import cn.njcit.domain.user.SClass;
import cn.njcit.domain.user.User;
import cn.njcit.service.user.UserService;
import cn.njcit.web.controller.leave.LeaveItem;
import cn.njcit.web.domain.DataTableForm;
import cn.njcit.web.service.user.WebUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            //将辅导员负责的班级和学管处负责的学院Id 保存到session中
            if(user.getRole().equals(AppConstants.INSTRUCTOR__ROLE)){//辅导员
                String teacherId = user.getUserId();
                Map teacherMap = new HashMap();
                teacherMap.put("teacherId",teacherId);
                List<Map> classes = webUserService.instructorGetManagedClass(teacherMap);
                List<SClass> managedClassList = new ArrayList<SClass>();
                if(classes!=null && classes.size()>0){
                    request.getSession().setAttribute("managedClasses",classes);
//                    for(Map classMap :classes){
//                        SClass sClass = new SClass();
//                        sClass.setClassId((String) classMap.get("class_id"));
//                        sClass.setClassName((String) classMap.get("class_name"));
//                        managedClassList.add(sClass);
//                    }
//                    user.setManagedClassList(managedClassList);
                }

            }else if(user.getRole()==AppConstants.STUDENT_PIPE_ROLE){//学管处
                request.getSession().setAttribute("managedColleageId",user.getColleageId());

            }
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



    /*================用户管理=========================*/

    /**
     * 学生管理界面首页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/studentManagerIndex")
    public String  studentManagerIndex(HttpServletRequest request,HttpServletResponse response){
        return "/web/user/studentManageIndex";
    }

    @RequestMapping(value = "/studentList",method = RequestMethod.POST)
    public @ResponseBody Map  studentList(UserQueryForm userQueryForm, HttpServletRequest request,HttpServletResponse response){
        //初始化datatable
        userQueryForm.initDataTable(request);
        User user = (User) request.getSession().getAttribute("user");
        //设置自己负责的学院  或  班级
        Object managedColleageIdOB = request.getSession().getAttribute("managedColleageId");
        String managedColleageId = managedColleageIdOB==null?null:managedColleageIdOB.toString();
        userQueryForm.setColleageId(managedColleageId);
        Object managedClassesOB = request.getSession().getAttribute("managedClasses");
        List<Map> classesList = managedClassesOB==null?null: (List<Map>) managedClassesOB;
        userQueryForm.setManagedClassList(classesList);
        //查询学生列表
        List<User> userList  = webUserService.queryStudentList(userQueryForm, user);
        int totalCount  = webUserService.queryStudentCount(userQueryForm,user);
        return CommonUtil.reurnDataTable(totalCount, userList, null);
    }


    /**
     * 重置学生密码
     * @param request
     * @return
     */
    @RequestMapping(value = "/resetStudentPassword",method = RequestMethod.POST)
    public @ResponseBody Map  resetStudentPassword(User updateUser, HttpServletRequest request,HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        //加密  sessionUserId+updateUserId+key
        //违法用户修改
        if(!updateUser.getToken().equals(MD5Util.md5Hex(user.getUserId()+updateUser.getUserId()+AppConstants.appConfig.getProperty("app.key")))){
            return null;
        }else{
            int count  = webUserService.resetStudentPassword(updateUser);
            if(count>0){
                return CommonUtil.ajaxSuccess(true);
            }else{
                return CommonUtil.ajaxFail(null,"密码重置失败，请联系管理员");
            }

        }

    }


    /**
     *获得所有的学院
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getColleages")
    public @ResponseBody Map  getColleages(HttpServletRequest request,HttpServletResponse response){
        List<Colleage> colleageList = webUserService.getColleages();

        return CommonUtil.ajaxSuccess(colleageList);
    }


    /**
     * 获得学院下的某个班级
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getClasses")
    public @ResponseBody Map  getClasses(Colleage colleage,HttpServletRequest request,HttpServletResponse response){
        List<TClass> classList = webUserService.getClasses(colleage);
        return CommonUtil.ajaxSuccess(classList);
    }



    /**
     * 新增一个学生
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/addStudent")
    public @ResponseBody Map  addStudent(Student student,HttpServletRequest request,HttpServletResponse response){
        student.setPassword("123456");
        try{
            int count  = webUserService.addStudent(student);
            if(count>0){
                return CommonUtil.ajaxSuccess(student);
            }else{
                return CommonUtil.ajaxFail(null,"学生信息添加失败，请联系管理员");
            }
        }catch(DuplicateKeyException dke){//插入重复数据学号
            return CommonUtil.ajaxFail(null,"该学号已存在，不能重复添加");
        }
    }

    /**
     * 删除学生
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteStudent")
    public @ResponseBody Map  deleteStudent(HttpServletRequest request,HttpServletResponse response){
        String token = request.getParameter("token");
        String studentId = request.getParameter("studentId");
        User sessionUser = (User) request.getSession().getAttribute("user");
        String confirmToken =MD5Util.md5Hex(sessionUser.getUserId()+studentId+AppConstants.appConfig.getProperty("app.key"));
        if(!confirmToken.equals(token)){
            return CommonUtil.ajaxFail(null,"不合法");
        }
        int count  = webUserService.deleteStudent(studentId);
        if(count>0){
            return CommonUtil.ajaxSuccess(null);
        }else{
            return CommonUtil.ajaxFail(null,"删除失败");
        }
    }

    /**
     * 编辑学生信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/editStudent")
    public @ResponseBody Map  editStudent(Student student,HttpServletRequest request,HttpServletResponse response){
        String token = request.getParameter("token");
        String studentId = request.getParameter("studentId");
        User sessionUser = (User) request.getSession().getAttribute("user");
        String confirmToken =MD5Util.md5Hex(sessionUser.getUserId()+studentId+AppConstants.appConfig.getProperty("app.key"));
        if(!confirmToken.equals(token)){
            return CommonUtil.ajaxFail(null,"不合法");
        }
        try{
            int count  = webUserService.editStudent(student);
            if(count>0){
                return CommonUtil.ajaxSuccess(null);
            }else{
                return CommonUtil.ajaxFail(null,"学生信息编辑失败,请联系管理员");
            }
        }catch(DuplicateKeyException dke){//插入重复数据学号
            return CommonUtil.ajaxFail(null,"该学号已存在");
        }
    }





    /**
     * 老师管理界面首页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/teacherManagerIndex")
    public String  teacherManagerIndex(HttpServletRequest request,HttpServletResponse response){
        return "/web/user/teacherManageIndex";
    }



    @RequestMapping(value = "/teacherList",method = RequestMethod.POST)
    public @ResponseBody Map  teacherList(UserQueryForm userQueryForm, HttpServletRequest request,HttpServletResponse response){
        //初始化datatable
        userQueryForm.initDataTable(request);
        User user = (User) request.getSession().getAttribute("user");
        //设置自己负责的学院  或  班级
        Object managedColleageIdOB = request.getSession().getAttribute("managedColleageId");
        String managedColleageId = managedColleageIdOB==null?null:managedColleageIdOB.toString();
        userQueryForm.setColleageId(managedColleageId);
        Object managedClassesOB = request.getSession().getAttribute("managedClasses");
        List<Map> classesList = managedClassesOB==null?null: (List<Map>) managedClassesOB;
        userQueryForm.setManagedClassList(classesList);
        //查询学生列表
        List<User> userList  = webUserService.queryTeacherList(userQueryForm, user);
        int totalCount  = webUserService.queryTeacherCount(userQueryForm,user);
        return CommonUtil.reurnDataTable(totalCount, userList, null);
    }

    @RequestMapping(value = "/resetTeacherPassword",method = RequestMethod.POST)
    public @ResponseBody Map  resetTeacherPassword(User updateUser,  HttpServletRequest request,HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        //加密  sessionUserId+updateUserId+key
        //违法用户修改
        if(!updateUser.getToken().equals(MD5Util.md5Hex(user.getUserId()+updateUser.getUserId()+AppConstants.appConfig.getProperty("app.key")))){
            return null;
        }else{
            int count  = webUserService.resetTeacherPassword(updateUser);
            if(count>0){
                return CommonUtil.ajaxSuccess(true);
            }else{
                return CommonUtil.ajaxFail(null,"密码重置失败，请联系管理员");
            }

        }
    }

    /**
     * 删除 辅导员
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteTeacher")
    public @ResponseBody Map  deleteTeacher(HttpServletRequest request,HttpServletResponse response){
        String token = request.getParameter("token");
        String teacherId = request.getParameter("teacherId");
        String role = request.getParameter("role");//当前删除老师的角色，要通过该角色来删除老师负责的班级
        User sessionUser = (User) request.getSession().getAttribute("user");
        String confirmToken =MD5Util.md5Hex(sessionUser.getUserId()+teacherId+AppConstants.appConfig.getProperty("app.key"));
        if(!confirmToken.equals(token)){
            return CommonUtil.ajaxFail(null,"不合法");
        }
        if(StringUtils.isEmpty(role)){
            return CommonUtil.ajaxFail(null,"不合法");
        }
        int count  = webUserService.deleteTeacher(teacherId,Integer.parseInt(role));
        if(count>0){
            return CommonUtil.ajaxSuccess(null);
        }else{
            return CommonUtil.ajaxFail(null,"删除失败");
        }
    }

    /**
     * 编辑老师信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/editTeacher")
    public @ResponseBody Map  editTeacher(Teacher teacher,HttpServletRequest request,HttpServletResponse response){
        String token = request.getParameter("token");
        String teacherId = request.getParameter("teacherId");
        User sessionUser = (User) request.getSession().getAttribute("user");
        String confirmToken =MD5Util.md5Hex(sessionUser.getUserId()+teacherId+AppConstants.appConfig.getProperty("app.key"));
        if(!confirmToken.equals(token)){
            return CommonUtil.ajaxFail(null,"不合法");
        }
        try{
            int count  = webUserService.editTeacher(teacher);
            if(count>0){
                return CommonUtil.ajaxSuccess(null);
            }else{
                return CommonUtil.ajaxFail(null,"老师信息编辑失败,请联系管理员");
            }
        }catch(DuplicateKeyException dke){//插入重复数据学号
            return CommonUtil.ajaxFail(null,"该工号已存在");
        }
    }

    /**
     * 新增一个老师
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/addTeacher")
    public @ResponseBody Map  addTeacher(Teacher teacher,HttpServletRequest request,HttpServletResponse response){
        User sessionUser = (User) request.getSession().getAttribute("user");
        teacher.setPassword("123456");
        try{
            int count  = webUserService.addTeacher(teacher,sessionUser);
            if(count>0){
                return CommonUtil.ajaxSuccess(teacher);
            }else{
                return CommonUtil.ajaxFail(null,"老师信息添加失败，请联系管理员");
            }
        }catch(DuplicateKeyException dke){//插入重复数据学号
            return CommonUtil.ajaxFail(null,"该工号已存在，不能重复添加");
        }
    }

    /**
     *显示调整 老师 负责的 班级
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getTeacherClassList")
    public @ResponseBody Map getTeacherClassList(TClassQueryForm tClassQueryForm,HttpServletRequest request,HttpServletResponse response){
        tClassQueryForm.initDataTable(request);
        String userId = request.getParameter("userId");
        String token = request.getParameter("token");
        User sessionUser = (User) request.getSession().getAttribute("user");
        String confirmToken =MD5Util.md5Hex(sessionUser.getUserId()+userId+AppConstants.appConfig.getProperty("app.key"));
        if(!confirmToken.equals(token)){
            return null;
        }
        List<TClass> classList = webUserService.getTeacherClassList(tClassQueryForm,sessionUser);
        int  total  = webUserService.getTeacherClassCount(tClassQueryForm,sessionUser);
        return CommonUtil.reurnDataTable(total,classList,null);
    }


}
