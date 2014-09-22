package cn.njcit.service.user.impl;

import cn.njcit.common.constants.AppConstants;
import cn.njcit.common.exception.ParameterException;
import cn.njcit.common.exception.ServiceException;
import cn.njcit.core.redis.RedisInstance;
import cn.njcit.dao.user.UserDao;
import cn.njcit.domain.user.User;
import cn.njcit.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户相关的操作
 * Created by YK on 2014-06-15.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisInstance redisInstance;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public User logIn(User reqUser) throws ParameterException, ServiceException {
        //如果用户查找成功，清空user中的密码，然后，返回User对象。并且把user放入redis。
        //根据角色类型来标记redis的key 。老师是tea_userId  学生是 stu_userId
        boolean isStudent=false;
        User user = null;
        if(AppConstants.STUDENT_ROLE.equals(reqUser.getRole())){//学生
            user = userDao.getStudent(reqUser);
            isStudent=true;
        }else if(AppConstants.INSTRUCTOR__ROLE.equals(reqUser.getRole())||AppConstants.STUDENT_PIPE_ROLE.equals(reqUser.getRole())){//辅导员
            user = userDao.getTeacher(reqUser);
            if(user.getRole()!=reqUser.getRole()){
                throw new ServiceException("权限不对");
            }
        }
        if(user!=null){
            user.setRole(reqUser.getRole());
            //将查询出来的学生信息或老师信息缓存到redis中
            String userId = user.getUserId();
            String userJson = "";
            redisInstance.saveUserInfo(userId,user);
        }
        return user;
    }


    @Override
    public List<Map> getTeacherManagedClass(Map reqMap) {
        List<Map> classes = null;
        String userId = (String) reqMap.get("userId");
        User user  = redisInstance.getUserInfo(userId);
        int role = user.getRole();
        if(AppConstants.INSTRUCTOR__ROLE.intValue()==role){//辅导员角色
            //辅导员，根据教师编号来获取其负责的班级
            reqMap.put("teacherId",userId);
            classes = userDao.getClassesByTeacherId(reqMap);
        }else if(AppConstants.STUDENT_PIPE_ROLE.intValue()==role){//学管处角色
            Integer colleageId = user.getColleageId();
            reqMap.put("colleageId",colleageId);
            classes = userDao.getClassesByColleageId(reqMap);
        }
        return classes;
    }

    @Override
    public int updatePassword(User reqUser) {
        int role = reqUser.getRole();
        int  count =0 ;
        if(role==AppConstants.STUDENT_ROLE.intValue()){//学生
            count = userDao.updateStudentPassword(reqUser);
        }else{//其他角色  包括辅导员 ，学管处
            count = userDao.updateTeacherPassword(reqUser);
        }

        return count;
    }

    public User getUniqueUser(User queryUser){
        int role = queryUser.getRole();
        User user = null;
        if(role==AppConstants.STUDENT_ROLE.intValue()){
            user  = userDao.getStudentWithPassword(queryUser);
        }else{
            user  = userDao.getTeacherWithPassword(queryUser);
        }
        return user;
    }
}
