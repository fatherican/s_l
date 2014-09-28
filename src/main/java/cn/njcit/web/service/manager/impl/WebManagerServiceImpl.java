package cn.njcit.web.service.manager.impl;

import cn.njcit.web.dao.manager.WebManagerDao;
import cn.njcit.web.service.manager.WebManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by YK on 2014/9/26.
 */
@Service("managerService")
public class WebManagerServiceImpl implements WebManagerService {

    @Autowired
    private WebManagerDao webManagerDao;



}
