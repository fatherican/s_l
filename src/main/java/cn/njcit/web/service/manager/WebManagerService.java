package cn.njcit.web.service.manager;

import cn.njcit.domain.user.User;
import cn.njcit.web.controller.manager.ColleageQueryForm;
import cn.njcit.web.controller.user.Colleage;
import cn.njcit.web.controller.user.TClass;
import cn.njcit.web.controller.user.TClassQueryForm;

import java.util.List;

/**
 * Created by YK on 2014/9/26.
 */
public interface WebManagerService {
    /**
     * 获得班级列表
     * @param classQueryForm
     * @param sessionUser
     * @return
     */
    List<TClass> getClassManageList(TClassQueryForm classQueryForm, User sessionUser);

    int getClassManageCount(TClassQueryForm classQueryForm, User sessionUser);

    /**
     * 学院列表查询
     * @param colleageQueryForm
     * @param sessionUser
     * @return
     */
    List<Colleage> getColleageManageList(ColleageQueryForm colleageQueryForm, User sessionUser);

    int getColleageManageCount(ColleageQueryForm colleageQueryForm, User sessionUser);

    int editColleage(Colleage colleage);

    int deleteColleage(Colleage colleage);

    int addColleage(Colleage colleage);

    int editClass(TClass tClass);

    int deleteClass(TClass tClass);

    int addClass(TClass tClass);
}
