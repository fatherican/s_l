package cn.njcit.web.service.leave.impl;


import cn.njcit.web.controller.leave.LeaveItem;
import cn.njcit.web.controller.leave.leaveQueryForm;
import cn.njcit.web.dao.leave.WebLeaveDao;
import cn.njcit.web.service.leave.WebLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by YK on 2014/9/12.
 */
@Service("webLeaveService")
public class WebLeaveServiceImpl implements WebLeaveService {

    @Autowired
    private WebLeaveDao webLeaveDao;

    @Override
    public List<LeaveItem> queryLeave(leaveQueryForm webQueryForm) {
        return webLeaveDao.queryLeave(webQueryForm);
    }
}
