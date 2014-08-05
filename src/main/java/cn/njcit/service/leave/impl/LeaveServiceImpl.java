package cn.njcit.service.leave.impl;

import cn.njcit.common.constants.AppConstants;
import cn.njcit.common.exception.ServiceException;
import cn.njcit.core.redis.RedisInstance;
import cn.njcit.dao.leave.LeaveDao;
import cn.njcit.dao.user.UserDao;
import cn.njcit.domain.leave.Leave;
import cn.njcit.domain.user.User;
import cn.njcit.service.leave.LeaveService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by YK on 2014-06-30.
 */
@Service("leaveService")
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveDao leaveDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisInstance redisInstance;


    @Override
    public int addLeave(Map reqMap) throws ServiceException {
        String leaveType = (String) reqMap.get("leaveType");//请假类型
        reqMap.put("createTime", DateFormatUtils.format(new Date(),"yyyy-MM-dd"));
        reqMap.put("needSecondApprove",false);//给予是否需要第二次审批一个默认值
       if(AppConstants.LEAVE_DAY==Integer.parseInt(leaveType)) {//天数请假
           String leaveDays = (String) reqMap.get("leaveDays");//请假天数
           if(Integer.parseInt(leaveDays)>Integer.parseInt(AppConstants.appConfig.getProperty("leave.needSecondApprovedDays"))){
               reqMap.put("needSecondApprove",true);
           }
        }
        String userId = (String) reqMap.get("userId");
        User user = redisInstance.getUserInfo(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String userInfo = JSON.toJSONString(user);
            Map userMap = objectMapper.readValue(userInfo,Map.class);
            reqMap.putAll(userMap);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("userInfo 反序列化异常");
        }
        int count = leaveDao. addLeave(reqMap);
        int leaveId = -1;
        if(count>0){
            leaveId = Integer.parseInt(String.valueOf(reqMap.get("leaveId")));
        }else{
            throw new ServiceException("请假失败，请重新尝试!");
        }
        return leaveId;
    }

    @Override
    public List<Leave> queryLeaveList(Map reqMap) {
        int begin = (Integer.parseInt(reqMap.get("pageNum").toString())-1)*Integer.parseInt(reqMap.get("pageSize").toString());
        int end = begin + Integer.parseInt(reqMap.get("pageSize").toString());
        reqMap.put("begin",begin);
        reqMap.put("end",end);
        List<Leave> resultList  = leaveDao.queryLeaveList(reqMap);
        converLeaveList(resultList);
        return resultList;
    }


    @Override
    public int delLeaveItem(Map reqMap) throws ServiceException {
        int count = 0;
        String userId = (String) reqMap.get("userId");
        String leaveId = (String) reqMap.get("leaveId");
        Leave leaveItem = leaveDao.getUniqueLeave(leaveId);
        if(leaveItem!=null){
            String studentId =leaveItem.getStudentId();
            String approved = leaveItem.getApproved();
            if(studentId.equals(userId) && "0".equals(approved)){
                count = leaveDao.delLeaveItem(reqMap);
            }else{
                throw new ServiceException("请假已被审批或请假不属于该用户");
            }
        }else{
            throw new ServiceException("没有该请假条目");
        }

        return count;
    }


//    @Transactional
    @Override
    public int updateLeaveApprovedState(Map reqMap) {
        String userId = (String) reqMap.get("userId");
        String leaveId = (String) reqMap.get("leaveId");
        User user = redisInstance.getUserInfo(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        int count = 0 ;
        if(user.getRole().equals(AppConstants.INSTRUCTOR__ROLE)){//辅导员角色
            reqMap.put("role",AppConstants.INSTRUCTOR__ROLE);
            //修改辅导员 审批状态
                // 判断 请假天数，如果请假天数超过两天，那么要修改 总审批状态（approved）为“待学管处审批",并添加上备注信息
                reqMap.put("instructorApproved",1);
                Leave leaveItem = leaveDao.getUniqueLeave(leaveId);
                String days = leaveItem.getLeaveDays();
                String leaveType = leaveItem.getLeaveType();//请假类型
                if(leaveType.equals(String.valueOf(AppConstants.LEAVE_DAY))){//天次请假
                    if(Integer.parseInt(days)>Integer.parseInt(AppConstants.appConfig.getProperty("leave.needSecondApprovedDays"))){
                        reqMap.put("approved",1);//辅导员已审批，学管处未审批
                    }else{
                        reqMap.put("approved",2);//辅导员已审批，无需学管处审批
                    }
                }else{
                    reqMap.put("approved",2);//辅导员已审批，无需学管处审批
                }
            count = leaveDao.updateLeaveApprovedState(reqMap);
        }else  if(user.getRole().equals(AppConstants.STUDENT_PIPE_ROLE)){//学管处角色
            reqMap.put("role",AppConstants.STUDENT_PIPE_ROLE);
            //修改   学管处审批状态 和  总审批状态,并添加上备注信息
            reqMap.put("studentPipeApproved",true);
            reqMap.put("approved",2);//学管处审批
            count = leaveDao.updateLeaveApprovedState(reqMap);
        }
        return count;
    }

    @Override
    public int sickLeave(Map reqMap) throws ServiceException {
        int count = 0 ;
        String userId = (String) reqMap.get("userId");
        String leaveId = (String) reqMap.get("leaveId");
        Leave leaveItem = leaveDao.getUniqueLeave(leaveId);
        String studentId = leaveItem.getStudentId();
        if(!studentId.equals(userId)){
            throw new  ServiceException("当前请假条不属于该用户!!!");
        }else{
            String approved = leaveItem.getApproved();
            if("2".equals(approved)){//已经审批成果的  请假条目，才允许被销假
                reqMap.put("leaveSicked",true);
                reqMap.put("leaveSickDate",DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
                count = leaveDao.sickLeave(reqMap);
            }
        }
        return count;
    }

    @Override
    public List<Leave> getLeaveList(Map reqMap) {
        String pageNum = (String) reqMap.get("pageNum");
        String pageSize = (String) reqMap.get("pageSize");
        Integer start = (Integer.parseInt(pageNum)-1)*(Integer.parseInt(pageSize));
        Integer end =start + (Integer.parseInt(pageSize));
        reqMap.put("start",start);
        reqMap.put("end",end);
        List<Leave> leaveList = leaveDao.getLeaveList(reqMap);
       //TODO converLeaveList(leaveList);
        return leaveList;
    }

    @Override
    public List<Leave> getTeacherMangagedLeaveList(Map reqMap){
        String pageNum = (String) reqMap.get("pageNum");
        String pageSize = (String) reqMap.get("pageSize");
        Integer start = (Integer.parseInt(pageNum)-1)*(Integer.parseInt(pageSize));
        Integer end =start + (Integer.parseInt(pageSize));
        reqMap.put("start",start);
        reqMap.put("end",end);

        String userId = (String) reqMap.get("userId");
        User user = redisInstance.getUserInfo(userId);
        String viewType= (String) reqMap.get("viewType");
        if("1".equals(viewType)){//按时间查看，  只能查看自己负责的班级
            if(user.getRole().equals(AppConstants.INSTRUCTOR__ROLE)) {//辅导员角色
                //只允许查看自己负责的班级
              String teacherId = user.getUserId();
              Map teacherMap = new HashMap();
              teacherMap.put("teacherId",teacherId);
              List<Map> classes = userDao.getClassesByTeacherId(reqMap);
              int i=0;
              String classIds[] = new String[classes.size()];
              for(Map map : classes){
                  classIds[i]= (String) map.get("class_id");
                  i++;
              }
              reqMap.put("classes",classIds);
            }else if(user.getRole()==AppConstants.STUDENT_PIPE_ROLE){//学管处角色
                Integer colleageId = user.getColleageId();
                reqMap.put("colleageId",colleageId);
            }

        }else if("2".equals(viewType)){//按照班级查看

        }
        List<Leave> leaveList = leaveDao.getLeaveList(reqMap);
        //TODO   converLeaveList(leaveList);
        return leaveList;
    }

    @Override
    public List<Leave> getSickedLeaveList(Map reqMap) {
        String pageNum = (String) reqMap.get("pageNum");
        String pageSize = (String) reqMap.get("pageSize");
        Integer start = (Integer.parseInt(pageNum)-1)*(Integer.parseInt(pageSize));
        Integer end =start + (Integer.parseInt(pageSize));
        reqMap.put("start",start);
        reqMap.put("end",end);
        List<Leave> leaveList  = null;
        String userId = (String) reqMap.get("userId");
        User user = redisInstance.getUserInfo(userId);
        Integer role = user.getRole();
        if(role!=AppConstants.STUDENT_ROLE){
            reqMap.put("leave_sicked",1);
            if(role==AppConstants.INSTRUCTOR__ROLE){//当前用户辅导员角色
                  reqMap.put("teacherId",user.getUserId());
                  List<Map> classesList = userDao.getClassesByTeacherId(reqMap);
                  reqMap.put("classList",classesList);
            }else if(role==AppConstants.STUDENT_PIPE_ROLE){//学管处角色
                  Integer colleageId = user.getColleageId();
                  reqMap.put("colleageId",colleageId);
            }
            leaveList  = leaveDao.getSickLeaveList(reqMap);
            //TODO   converLeaveList(leaveList);
        }
        return leaveList;
    }


    /**
     * 把请假中的秒转换成日期
     * @param leaveList
     */
    private void converLeaveList( List<Leave> leaveList ){
        try {
            if(leaveList!=null  &&  leaveList.size()>0){
                for(Leave leave : leaveList){
                    if(leave.getLeaveStartDate()!=null && StringUtils.isNotEmpty(leave.getLeaveStartDate())){
                        Date leave_start_date = null;
                        leave_start_date = DateUtils.parseDate(leave.getLeaveStartDate(), new String[]{"yyyy-MM-dd"});
                        String leave_start_dateStr = DateFormatUtils.format(leave_start_date, "yyyy-MM-dd");
                        leave.setLeaveStartDate(leave_start_dateStr);
                    }
                    if(leave.getLeaveEndDate()!=null &&  StringUtils.isNotEmpty(leave.getLeaveEndDate())){
                        Date leave_end_date = DateUtils.parseDate(leave.getLeaveEndDate(), new String[]{"yyyy-MM-dd"});
                        String leave_end_dateStr = DateFormatUtils.format(leave_end_date, "yyyy-MM-dd");
                        leave.setLeaveEndDate(leave_end_dateStr);
                    }
                    if(leave.getCreateTime()!=null &&  StringUtils.isNotEmpty(leave.getCreateTime())){
                        Date create_time = DateUtils.parseDate(leave.getCreateTime(), new String[]{"yyyy-MM-dd HH:mm:ss.S"});
                        String create_timeStr = DateFormatUtils.format(create_time,"yyyy-MM-dd HH:mm:ss");
                        leave.setCreateTime(create_timeStr);
                    }
                    if(leave.getLeaveSickDate()!=null &&  StringUtils.isNotEmpty(leave.getLeaveSickDate())){
                        Date leave_sick_date =DateUtils.parseDate(leave.getLeaveSickDate(), new String[]{"yyyy-MM-dd HH:mm:ss.S"});
                        String leave_sick_dateStr =  DateFormatUtils.format(leave_sick_date, "yyyy-MM-dd HH:mm:ss");
                        leave.setLeaveSickDate(leave_sick_dateStr);
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

   /* private String seconds2DateStr(String date,String pattern){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(date));
        return DateFormatUtils.format(calendar,pattern);
    }*/


}
