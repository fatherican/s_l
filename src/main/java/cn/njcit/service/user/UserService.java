package cn.njcit.service.user;

import cn.njcit.common.exception.ParameterException;
import cn.njcit.domain.user.User;

/**
 * Created by YK on 2014-06-15.
 */
public interface UserService {

    User logIn(User reqUser) throws ParameterException;
}
