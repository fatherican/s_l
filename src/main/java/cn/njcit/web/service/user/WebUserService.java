package cn.njcit.web.service.user;

import cn.njcit.domain.user.User;
import cn.njcit.web.controller.leave.LeaveItem;
import cn.njcit.web.controller.user.*;

import java.util.List;
import java.util.Map;

/**
 * Created by YK on 2014/9/11.
 */
public interface WebUserService {

    public User login(UserQueryForm userQueryForm);

    int updatePassword(UserQueryForm userForm);

    /**
     * 辅导员获得自己负责的班级
     * @param teacherMap
     * @return
     */
    List<Map> instructorGetManagedClass(Map teacherMap);

    /**
     * 查询学生列表
     * @param userQueryForm
     * @param user
     * @return
     */
    List<User> queryStudentList(UserQueryForm userQueryForm, User user);

    int queryStudentCount(UserQueryForm userQueryForm, User user);

    int resetStudentPassword(User updateUser);

    /**
     * 获取所有的学院
     * @return
     */
    List<Colleage> getColleages();

    List<TClass> getClasses(Colleage colleage);

    int addStudent(Student student);

    int deleteStudent(String studentId);

    int editStudent(Student student);

    /**
     * 查询教师列表
     * @param userQueryForm
     * @param user
     * @return
     */
    List<User> queryTeacherList(UserQueryForm userQueryForm, User user);


    int queryTeacherCount(UserQueryForm userQueryForm, User user);

    /**
     * 更新辅导员的密码
     * @param updateUser
     * @return
     */
    int resetTeacherPassword(User updateUser);

    int deleteTeacher(String teacherId,int role);

    int editTeacher(Teacher teacher);

    int addTeacher(Teacher teacher, User sessionUser);

    /**
     * 获得老师和班级 之间的关系数据
     * @param tClassQueryForm
     * @return
     */
    List<TClass> getTeacherClassList(TClassQueryForm tClassQueryForm,User sessionUser);

    int getTeacherClassCount(TClassQueryForm tClassQueryForm,User sessionUser);

    int removeManagedClass(TClassQueryForm queryForm);

    int addManagedClass(TClassQueryForm queryForm);
}
