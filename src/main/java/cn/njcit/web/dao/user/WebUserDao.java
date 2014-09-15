package cn.njcit.web.dao.user;

import cn.njcit.domain.user.User;
import cn.njcit.web.controller.user.UserQueryForm;

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
}
