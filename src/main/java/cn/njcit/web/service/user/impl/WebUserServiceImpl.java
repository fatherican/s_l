package cn.njcit.web.service.user.impl;

import cn.njcit.common.constants.AppConstants;
import cn.njcit.common.util.encrypt.MD5Util;
import cn.njcit.dao.user.UserDao;
import cn.njcit.domain.user.User;
import cn.njcit.web.controller.user.UserQueryForm;
import cn.njcit.web.dao.user.WebUserDao;
import cn.njcit.web.service.user.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
