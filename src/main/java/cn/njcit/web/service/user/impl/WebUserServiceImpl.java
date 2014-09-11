package cn.njcit.web.service.user.impl;

import cn.njcit.common.util.encrypt.MD5Util;
import cn.njcit.domain.user.User;
import cn.njcit.web.controller.user.UserQueryForm;
import cn.njcit.web.dao.user.WebUserDao;
import cn.njcit.web.service.user.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by YK on 2014/9/11.
 */
@Service("webUserService")
public class WebUserServiceImpl implements WebUserService{

    @Autowired
    private WebUserDao webUserDao;

    @Override
    public User login(UserQueryForm userQueryForm) {
        String password = userQueryForm.getPassword();
        password = MD5Util.md5Hex(password);
        userQueryForm.setPassword(password);
        User user = webUserDao.login(userQueryForm);
        return user;
    }

    @Override
    public int updatePassword(UserQueryForm userForm) {
        int count = webUserDao.updatePassword(userForm);
        return count;
    }
}
