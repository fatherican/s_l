package cn.njcit.dao.user;

import cn.njcit.domain.user.Student;
import cn.njcit.domain.user.Teacher;
import cn.njcit.domain.user.User;

/**
 * Created by YK on 2014-06-16.
 */
public interface UserDao {

    /**
     * 通过相关的user信息来获得对以后给的user
     * @param queryUser
     * @return
     */
    User getUser(User queryUser);

    /**
     * 获得老师的信息
     * @param queryUser
     * @return
     */
    Teacher getTeacher(User queryUser);

    /**
     * 获得老师的信息
     * @param queryUser
     * @return
     */
    Student getStudent(User queryUser);


}
