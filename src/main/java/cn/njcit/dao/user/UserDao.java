package cn.njcit.dao.user;

import cn.njcit.domain.user.User;

import java.util.List;
import java.util.Map;

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
    User getTeacher(User queryUser);

    /**
     * 获得老师的信息
     * @param queryUser
     * @return
     */
    User getStudent(User queryUser);

    /**
     * 获得老师所负责的班级,根据教师 Id
     * @param reqMap
     * @return
     */
    List<Map> getClassesByTeacherId(Map reqMap);

    /**
     * 根据学院号，来获得该学员的所有班级
     * @param reqMap
     * @return
     */
    List<Map> getClassesByColleageId(Map reqMap);

    User getStudentWithPassword(User queryUser);

    int updateStudentPassword(User reqUser);

    User getTeacherWithPassword(User queryUser);

    int updateTeacherPassword(User reqUser);
}
