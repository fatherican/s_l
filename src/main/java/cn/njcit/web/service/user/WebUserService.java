package cn.njcit.web.service.user;

import cn.njcit.domain.user.User;
import cn.njcit.web.controller.leave.LeaveItem;
import cn.njcit.web.controller.user.UserQueryForm;

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
}
