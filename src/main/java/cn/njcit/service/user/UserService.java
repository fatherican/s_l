package cn.njcit.service.user;

import cn.njcit.common.exception.ParameterException;
import cn.njcit.common.exception.ServiceException;
import cn.njcit.domain.user.User;

import java.util.List;
import java.util.Map;

/**
 * Created by YK on 2014-06-15.
 */
public interface UserService {

    User logIn(User reqUser) throws ParameterException, ServiceException;

    /**
     * 获得老师负责的班级
     * ①userId
     * @param reqMap
     * @return
     */
    List<Map> getTeacherManagedClass(Map reqMap);


    User getUniqueUser(User queryUser);

    int updatePassword(User reqUser);
}
