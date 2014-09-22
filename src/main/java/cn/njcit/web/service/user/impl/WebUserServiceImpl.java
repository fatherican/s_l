package cn.njcit.web.service.user.impl;

import cn.njcit.common.constants.AppConstants;
import cn.njcit.common.util.CommonUtil;
import cn.njcit.common.util.UID;
import cn.njcit.common.util.encrypt.MD5Util;
import cn.njcit.dao.user.UserDao;
import cn.njcit.domain.user.User;
import cn.njcit.web.controller.user.*;
import cn.njcit.web.dao.user.WebUserDao;
import cn.njcit.web.service.user.WebUserService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by YK on 2014/9/11.
 */
@Service("webUserService")
public class WebUserServiceImpl implements WebUserService{

    @Autowired
    private WebUserDao webUserDao;

    @Autowired
    private UserDao userDao;

    @Override
    public User login(UserQueryForm userQueryForm) {
        String password = userQueryForm.getPassword();
        password = MD5Util.md5Hex(password);
        userQueryForm.setPassword(password);
        User user = webUserDao.login(userQueryForm);
        return user;
    }

    @Override
    public int updatePassword(UserQueryForm userForm) {
        int count = webUserDao.updatePassword(userForm);
        return count;
    }

    @Override
    public List<Map> instructorGetManagedClass(Map teacherMap) {
        List<Map> classes = userDao.getClassesByTeacherId(teacherMap);
        return classes;
    }

    @Override
    public List<User> queryStudentList(UserQueryForm userQueryForm, User user) {
        List<User> users = webUserDao.queryStudentList(userQueryForm);
        if(users!=null){
            for(User userItem : users){
                //对每一个用户生成一个token,当对该用户进行编辑操作时需要验证token
                //加密  sessionUserId+updateUserId+key
                userItem.setToken(MD5Util.md5Hex(user.getUserId()+userItem.getUserId()+AppConstants.appConfig.getProperty("app.key")));
            }
        }
        return users;
    }

    @Override
    public int queryStudentCount(UserQueryForm userQueryForm, User user) {
        return webUserDao.queryStudentCount(userQueryForm);
    }

    @Override
    public int resetStudentPassword(User updateUser) {
        updateUser.setPassword(MD5Util.md5Hex(updateUser.getPassword()));
        int count  = webUserDao.resetStudentPassword(updateUser);
        return count;
    }

    @Override
    public List<Colleage> getColleages() {
        List<Colleage> colleageList = webUserDao.getColleages();
        return colleageList;
    }

    @Override
    public List<TClass> getClasses(Colleage colleage) {
        List<TClass> classList = webUserDao.getClasses(colleage);
        return classList;
    }

    @Override
    public int addStudent(Student student) {
        student.setCreateTime(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        student.setPassword(MD5Util.md5Hex(student.getPassword()));
        student.setStudentId(String.valueOf(UID.getUID()));
        int count = webUserDao.addStudent(student);
        return count;
    }

    @Override
    public int deleteStudent(String studentId) {
        int count = webUserDao.deleteStudent(studentId);
        return count;
    }

    @Override
    public int editStudent(Student student) {
        int count = webUserDao.editStudent(student);
        return count;
    }

    @Override
    public List<User> queryTeacherList(UserQueryForm userQueryForm, User user) {
        userQueryForm = initTeacherListForm(userQueryForm,user);
        if(user.getRole().intValue()!=AppConstants.STUDENT_PIPE_ROLE.intValue() && user.getRole().intValue()!=AppConstants.ADMIN_ROLE){// 既不是学管处又不是管理员
            return null;
        }
        List<User> users = webUserDao.queryTeacherList(userQueryForm);
        if(users!=null){
            for(User userItem : users){
                //对每一个用户生成一个token,当对该用户进行编辑操作时需要验证token
                //加密  sessionUserId+updateUserId+key
                userItem.setToken(MD5Util.md5Hex(user.getUserId()+userItem.getUserId()+AppConstants.appConfig.getProperty("app.key")));
            }
        }
        return users;
    }

    @Override
    public int queryTeacherCount(UserQueryForm userQueryForm, User user) {
        userQueryForm = initTeacherListForm(userQueryForm,user);
        if(user.getRole().intValue()!=AppConstants.STUDENT_PIPE_ROLE.intValue() && user.getRole().intValue()!=AppConstants.ADMIN_ROLE){// 既不是学管处又不是管理员
            return 0;
        }
        int count  = webUserDao.queryTeacherCount(userQueryForm);

        return count;
    }

    /*初始化查询教师列表的Form*/
    private UserQueryForm initTeacherListForm(UserQueryForm userQueryForm, User user){
        if(user.getRole().intValue()==AppConstants.STUDENT_PIPE_ROLE.intValue()){//学管处
            userQueryForm.setColleageId(String.valueOf(user.getColleageId()));
            userQueryForm.setRole(String.valueOf(AppConstants.INSTRUCTOR__ROLE));//学管处只允许查看辅导员老师的信息
        }else if(user.getRole().intValue()==AppConstants.ADMIN_ROLE){//管理员角色
            List<String> roles = new ArrayList<String>();
            roles.add(AppConstants.INSTRUCTOR__ROLE.toString());
            roles.add(AppConstants.STUDENT_PIPE_ROLE.toString());
            userQueryForm.setRoles(roles);
        }
        return userQueryForm;
    }

    @Override
    public int resetTeacherPassword(User updateUser) {
        updateUser.setPassword(MD5Util.md5Hex(updateUser.getPassword()));
        int count  = webUserDao.resetTeacherPassword(updateUser);
        return count;
    }

    @Override
    @Transactional
    public int deleteTeacher(String teacherId,int role) {
        int count = webUserDao.deleteTeacher(teacherId);
         //同事删除该老师负责的班级
        if(role ==  AppConstants.STUDENT_ROLE.intValue()){//学管处

        }else  if(role == AppConstants.INSTRUCTOR__ROLE.intValue()){//辅导员
            webUserDao.deleteManagedClass(teacherId);
        }
        return count;
    }

    @Override
    public int editTeacher(Teacher teacher) {
        int count = webUserDao.editTeacher(teacher);
        return count;
    }

    @Override
    public int addTeacher(Teacher teacher,User sessionUser) {
        teacher.setCreateTime(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        teacher.setPassword(MD5Util.md5Hex(teacher.getPassword()));
        if(sessionUser.getRole().intValue() == AppConstants.STUDENT_PIPE_ROLE.intValue()){//学管处只能添加辅导员角色的老师
            teacher.setRole(AppConstants.INSTRUCTOR__ROLE.toString());
        }
        teacher.setTeacherId(String.valueOf(UID.getUID()));
        int count = webUserDao.addTeacher(teacher);
        return count;
    }

    @Override
    public List<TClass> getTeacherClassList(TClassQueryForm tClassQueryForm,User sessionUser) {
        initTClassQueryForm(tClassQueryForm,sessionUser);
        List<TClass> classList = webUserDao.getTeacherClassList(tClassQueryForm);
        return classList;
    }


    @Override
    public int getTeacherClassCount(TClassQueryForm tClassQueryForm,User sessionUser) {
        initTClassQueryForm(tClassQueryForm,sessionUser);
        int  count  = webUserDao.getTeacherClassCount(tClassQueryForm);
        return count;
    }

    private TClassQueryForm initTClassQueryForm(TClassQueryForm tClassQueryForm,User sessionUser){
        if(sessionUser.getRole().intValue()==AppConstants.STUDENT_PIPE_ROLE){
            tClassQueryForm.setColleageId(sessionUser.getColleageId().toString());
        }
        if("0".equals(tClassQueryForm.getColleageId())){
            tClassQueryForm.setColleageId("");
        }
        return tClassQueryForm;
    }
}
