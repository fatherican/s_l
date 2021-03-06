package cn.njcit.web.dao.manager;

import cn.njcit.web.controller.leave.LeaveCheckForm;
import cn.njcit.web.controller.leave.LeaveItem;
import cn.njcit.web.controller.leave.leaveQueryForm;
import cn.njcit.web.controller.manager.ColleageQueryForm;
import cn.njcit.web.controller.user.*;

import java.util.List;

/**
 * Created by YK on 2014/9/12.
 */
public interface WebManagerDao {

    List<TClass> getClassManageList(TClassQueryForm classQueryForm);

    int getClassManageCount(TClassQueryForm classQueryForm);

    List<Colleage> getColleageManageList(ColleageQueryForm colleageQueryForm);

    int getColleageManageCount(ColleageQueryForm colleageQueryForm);

    int editColleage(Colleage colleage);

    int deleteColleage(Colleage colleage);

    int addColleage(Colleage colleage);

    int editClass(TClass tClass);

    int deleteClass(TClass tClass);

    int addClass(TClass tClass);

    int batchInsertClass(List<TClass> tClassList);

    TClass getTClass(String className);

    int batchInsertStudent(List<Student> studentList);

    int batchInsertTeacher(List<Teacher> teacherList);
}
