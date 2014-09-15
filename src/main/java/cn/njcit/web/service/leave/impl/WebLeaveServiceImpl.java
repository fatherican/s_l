package cn.njcit.web.service.leave.impl;


import cn.njcit.common.constants.AppConstants;
import cn.njcit.dao.leave.LeaveDao;
import cn.njcit.dao.user.UserDao;
import cn.njcit.domain.leave.Leave;
import cn.njcit.domain.user.User;
import cn.njcit.web.controller.leave.LeaveCheckForm;
import cn.njcit.web.controller.leave.LeaveItem;
import cn.njcit.web.controller.leave.leaveQueryForm;
import cn.njcit.web.dao.leave.WebLeaveDao;
import cn.njcit.web.service.leave.WebLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by YK on 2014/9/12.
 */
@Service("webLeaveService")
public class WebLeaveServiceImpl implements WebLeaveService {

    @Autowired
    private WebLeaveDao webLeaveDao;
    @Autowired
    private LeaveDao leaveDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<LeaveItem> queryLeave(leaveQueryForm webQueryForm, User user) {



        return webLeaveDao.queryLeave(webQueryForm);
    }

    @Override
    public List<LeaveItem> queryUnCheckedLeave(leaveQueryForm webQueryForm, User user) {
        if(user.getRole().intValue()==AppConstants.INSTRUCTOR__ROLE.intValue() || user.getRole().intValue()==AppConstants.ADMIN_ROLE.intValue() ){//辅导员,或 超级管理员
            webQueryForm.setApproved("-1");
        }else if(user.getRole().intValue()==AppConstants.STUDENT_PIPE_ROLE.intValue()){//学管处
            webQueryForm.setApproved("2");
        }

        return webLeaveDao.queryLeave(webQueryForm);
    }

    @Override
    public int queryUnCheckedLeaveCount(leaveQueryForm webQueryForm, User user) {
        if(user.getRole().intValue()==AppConstants.INSTRUCTOR__ROLE.intValue() || user.getRole().intValue()==AppConstants.INSTRUCTOR__ROLE.intValue()){//辅导员
            webQueryForm.setApproved("-1");
        }else if(user.getRole().intValue()==AppConstants.STUDENT_PIPE_ROLE.intValue()){//学管处
            webQueryForm.setApproved("2");
        }

        return webLeaveDao.queryLeaveCount(webQueryForm);
    }

    @Override
    public int checkLeave(LeaveCheckForm leaveCheckForm, User user) {
        Leave leave = leaveDao.getUniqueLeave(leaveCheckForm.getLeaveId());
        Integer currentUserRole = user.getRole();
        leaveCheckForm.setRole(currentUserRole);
        leaveCheckForm.setApprovedDate(new Date());//设置审批时间
        leaveCheckForm.setUserId(user.getUserId());//设置审批人
        if(user.getRole().intValue()==AppConstants.INSTRUCTOR__ROLE.intValue()){//辅导员
            //判断当前的这个辅导员的审批状态，如果辅导员审批结果为不同意，那么最终状态也是不同意
            //如果最终状态为已同意，则需要判断当前这个假条的状态是否需要，学管处审批，如果需要，那么
            //置最终状态为 2 辅导员已审批，需要学管处再次审批
            if("0".equals(leaveCheckForm.getApproved())){//不同意
                leaveCheckForm.setFinalApproved("0");
            }else{//同意
                if("1".equals(leave.getNeedSecondApprove())){//需要二次审批
                    leaveCheckForm.setFinalApproved("2");// 2辅导员已审批等待学管处审批
                }else{
                    leaveCheckForm.setFinalApproved("1");// 通过
                }
            }
        }else if(user.getRole().intValue()==AppConstants.STUDENT_PIPE_ROLE.intValue()){//学管处
            if("0".equals(leaveCheckForm.getApproved())){//不同意
                leaveCheckForm.setFinalApproved("0");
            }else if("1".equals(leaveCheckForm.getApproved())){//同意
                leaveCheckForm.setFinalApproved("1");// 通过
            }
        }else if(user.getRole().intValue()==AppConstants.ADMIN_ROLE.intValue()){//超级管理员，一键审核权
            leaveCheckForm.setFinalApproved(leaveCheckForm.getApproved());
        }
        int count = webLeaveDao.checkLeave(leaveCheckForm);

        return count;
    }

    @Override
    public List<LeaveItem> queryCheckedLeave(leaveQueryForm webQueryForm, User user) {
        if(user.getRole().intValue()==AppConstants.INSTRUCTOR__ROLE.intValue() || user.getRole().intValue()==AppConstants.INSTRUCTOR__ROLE.intValue() ){//辅导员 ，或超级管理员
            webQueryForm.setInstructorNotApproved("-1");
        }else if(user.getRole().intValue()==AppConstants.STUDENT_PIPE_ROLE.intValue()){//学管处
            List<String> approvedStates = new ArrayList<String>();
            approvedStates.add("0");
            approvedStates.add("1");
            webQueryForm.setApprovedStates(approvedStates);
        }
        return webLeaveDao.queryLeave(webQueryForm);
    }

    @Override
    public int queryCheckedLeaveCount(leaveQueryForm webQueryForm, User user) {
        if(user.getRole().intValue()==AppConstants.INSTRUCTOR__ROLE.intValue()  || user.getRole().intValue()==AppConstants.INSTRUCTOR__ROLE.intValue() ){//辅导员
            webQueryForm.setInstructorNotApproved("-1");
        }else if(user.getRole().intValue()==AppConstants.STUDENT_PIPE_ROLE.intValue()){//学管处
            webQueryForm.setStudentNotPipeApproved("-1");//学管处已经审批
        }
        return webLeaveDao.queryLeaveCount(webQueryForm);
    }
}
