package cn.njcit.service.user.impl;

import cn.njcit.common.constants.AppConstants;
import cn.njcit.common.exception.ParameterException;
import cn.njcit.dao.user.UserDao;
import cn.njcit.domain.user.Student;
import cn.njcit.domain.user.Teacher;
import cn.njcit.domain.user.User;
import cn.njcit.service.user.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 用户相关的操作
 * Created by YK on 2014-06-15.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public User logIn(User reqUser)  throws ParameterException {
        //如果用户查找成功，清空user中的密码，然后，返回User对象。并且把user放入redis。
        //根据角色类型来标记redis的key 。老师是tea_userId  学生是 stu_userId
        boolean isStudent=false;
        User user = null;
        if(AppConstants.STUDENT_ROLE.equals(reqUser.getRole())){//学生
            user = userDao.getStudent(reqUser);
            isStudent=true;
        }else if(AppConstants.INSTRUCTOR__ROLE.equals(reqUser.getRole())||AppConstants.STUDENT_PIPE_ROLE.equals(reqUser.getRole())){//辅导员
            user = userDao.getTeacher(reqUser);
        }
        if(user!=null){
            //将查询出来的学生信息或老师信息缓存到redis中
            String userId = user.getUserId();
            if(isStudent){
                Student student = (Student) user;
                String studentJson = JSON.toJSONString(student);
                stringRedisTemplate.opsForValue().set("stu_"+userId,studentJson,Long.parseLong(AppConstants.appConfig.getProperty("redis.expireTime")));
            }else{
                Teacher teacher = (Teacher) user;
                String teacherJson = JSON.toJSONString(teacher);
                stringRedisTemplate.opsForValue().set("tea_" + userId, teacherJson, Long.parseLong(AppConstants.appConfig.getProperty("redis.expireTime")));
            }
        }
        return user;
    }
}
