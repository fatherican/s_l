package cn.njcit.web.dao.user;

import cn.njcit.domain.user.User;
import cn.njcit.web.controller.user.UserQueryForm;

/**
 * Created by YK on 2014/9/11.
 */
public interface WebUserDao {
    public User login(UserQueryForm userQueryForm);

    int updatePassword(UserQueryForm userForm);
}