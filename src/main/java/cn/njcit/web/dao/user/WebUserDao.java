package cn.njcit.web.dao.user;

import cn.njcit.domain.user.User;
import cn.njcit.web.controller.user.*;

import java.util.List;

/**
 * Created by YK on 2014/9/11.
 */
public interface WebUserDao {
    public User login(UserQueryForm userQueryForm);

    int updatePassword(UserQueryForm userForm);

    List<User> queryStudentList(UserQueryForm userQueryForm);

    int queryStudentCount(UserQueryForm userQueryForm);

    int resetStudentPassword(User updateUser);

    List<Colleage> getColleages();

    List<TClass> getClasses(Colleage colleage);

    int addStudent(Student student);

    int deleteStudent(String studentId);

    int editStudent(Student student);

    List<User> queryTeacherList(UserQueryForm userQueryForm);

    int queryTeacherCount(UserQueryForm userQueryForm);

    int resetTeacherPassword(User updateUser);

    int deleteTeacher(String teacherId);

    int editTeacher(Teacher teacher);

    int addTeacher(Teacher teacher);

    List<TClass> getTeacherClassList(TClassQueryForm tClassQueryForm);

    int getTeacherClassCount(TClassQueryForm tClassQueryForm);

    int deleteManagedClass(String teacherId);
}
