package cn.njcit.web.service.manager.impl;

import cn.njcit.common.constants.AppConstants;
import cn.njcit.domain.user.User;
import cn.njcit.web.controller.manager.ColleageQueryForm;
import cn.njcit.web.controller.user.Colleage;
import cn.njcit.web.controller.user.TClass;
import cn.njcit.web.controller.user.TClassQueryForm;
import cn.njcit.web.dao.manager.WebManagerDao;
import cn.njcit.web.service.manager.WebManagerService;
import cn.njcit.web.service.user.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by YK on 2014/9/26.
 */
@Service("managerService")
public class WebManagerServiceImpl implements WebManagerService {


    @Autowired
    private WebManagerDao webManagerDao;


    @Override
    public List<TClass> getClassManageList(TClassQueryForm classQueryForm, User sessionUser) {
        if (initQueryClass(classQueryForm, sessionUser)) return null;
        List<TClass> classList = webManagerDao.getClassManageList(classQueryForm);
        return classList;
    }



    @Override
    public int getClassManageCount(TClassQueryForm classQueryForm, User sessionUser) {
        if (initQueryClass(classQueryForm, sessionUser)) return 0;
        int total = webManagerDao.getClassManageCount(classQueryForm);
        return total;
    }


    private boolean initQueryClass(TClassQueryForm classQueryForm, User sessionUser) {
        if(sessionUser.getRole().intValue() == AppConstants.STUDENT_PIPE_ROLE.intValue()){//学管处只能添加辅导员角色的老师
            classQueryForm.setColleageId(String.valueOf(sessionUser.getColleageId()));
        }else  if(sessionUser.getRole().intValue() == AppConstants.ADMIN_ROLE.intValue()){//学管处只能添加辅导员角色的老师
            classQueryForm.setColleageId(null);
        }else{
            return true;
        }
        return false;
    }


    @Override
    public List<Colleage> getColleageManageList(ColleageQueryForm colleageQueryForm, User sessionUser) {
        List<Colleage> colleageList = webManagerDao.getColleageManageList(colleageQueryForm);
        return colleageList;
    }

    @Override
    public int getColleageManageCount(ColleageQueryForm colleageQueryForm, User sessionUser) {
       int total= webManagerDao.getColleageManageCount(colleageQueryForm);
        return total;
    }

    @Override
    public int editColleage(Colleage colleage) {
        int count = webManagerDao.editColleage(colleage);
        return count;
    }

    @Override
    public int deleteColleage(Colleage colleage) {
        int count =  webManagerDao.deleteColleage(colleage);
        return count;

    }

    @Override
    public int addColleage(Colleage colleage) {
        int count = webManagerDao.addColleage(colleage);
        return count;
    }
}
