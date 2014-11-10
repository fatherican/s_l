package cn.njcit.service.leave.impl;

import cn.njcit.common.constants.AppConstants;
import cn.njcit.common.exception.ServiceException;
import cn.njcit.controller.leave.LeaveStatisticsQueryForm;
import cn.njcit.core.redis.RedisInstance;
import cn.njcit.dao.leave.LeaveDao;
import cn.njcit.dao.user.UserDao;
import cn.njcit.domain.leave.Leave;
import cn.njcit.domain.user.User;
import cn.njcit.service.leave.LeaveService;
import cn.njcit.service.user.UserService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @Autowired
    private UserService userService;


    @Override
    public int addLeave(Map reqMap) throws ServiceException {
        String leaveType = (String) reqMap.get("leaveType");//请假类型
        reqMap.put("createTime", DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
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
            if(studentId.equals(userId) && "-1".equals(approved)){
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
        String approvedState =(String) reqMap.get("approvedState");//审批状态 0 不同意 1  同意
        User user = redisInstance.getUserInfo(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        int count = 0 ;
        if(user.getRole().equals(AppConstants.INSTRUCTOR__ROLE)){//辅导员角色
            reqMap.put("role",AppConstants.INSTRUCTOR__ROLE);
            //修改辅导员 审批状态
                // 判断 请假天数，如果请假天数超过两天，那么要修改 总审批状态（approved）为“待学管处审批",并添加上备注信息

                Leave leaveItem = leaveDao.getUniqueLeave(leaveId);
                String days = leaveItem.getLeaveDays();
                String leaveType = leaveItem.getLeaveType();//请假类型
                if(leaveType.equals(String.valueOf(AppConstants.LEAVE_DAY))){//天次请假
                    if(Integer.parseInt(days)>Integer.parseInt(AppConstants.appConfig.getProperty("leave.needSecondApprovedDays"))){
                        if("0".equals(approvedState)){
                            reqMap.put("approved",0);//最终状态为0 不同意
                            reqMap.put("instructorApproved",0);//辅导员审批状态为0  不同意
                        }else if("1".equals(approvedState)){//同意
                            reqMap.put("approved",2);//最终状态为2 待学馆处审批
                            reqMap.put("instructorApproved",1);//辅导员审批状态为1 同意
                        }
                    }else{
                        setInstructorApprovedState(reqMap, approvedState);
                        reqMap.put("approved",1);//辅导员已审批，无需学管处审批
                    }
                }else{//课程请假，则此时的审批状态，就是最终的审批状态
                    setInstructorApprovedState(reqMap, approvedState);
                }
            count = leaveDao.updateLeaveApprovedState(reqMap);
        }else  if(user.getRole().equals(AppConstants.STUDENT_PIPE_ROLE)){//学管处角色
            reqMap.put("role",AppConstants.STUDENT_PIPE_ROLE);
            if("0".equals(approvedState)){
                reqMap.put("studentPipeApproved",0);//学管处审批状态为0  不同意
                reqMap.put("approved",0);//最终状态为0 不同意
            }else if("1".equals(approvedState)){//同意
                reqMap.put("studentPipeApproved",true);//学管处审批状态1 同意
                reqMap.put("approved",1);//最终状态为1 同意
            }
            count = leaveDao.updateLeaveApprovedState(reqMap);
        }
        return count;
    }

    private void setInstructorApprovedState(Map reqMap, String approvedState) {
        if("0".equals(approvedState)){
            reqMap.put("approved",0);//最终状态为0 不同意
            reqMap.put("instructorApproved",0);//辅导员审批状态为1  不同意
        }else if("1".equals(approvedState)){//同意
            reqMap.put("approved",1);//最终状态为1
            reqMap.put("instructorApproved",1);//辅导员审批状态为1 同意
        }
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
            if("1".equals(approved)){//已经审批成果的  请假条目，才允许被销假
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
              List<Map> classes = userDao.getClassesByTeacherId(teacherMap);
              int i=0;
              String classIds[] = new String[classes.size()];
              for(Map map : classes){
                  classIds[i]= String.valueOf(map.get("class_id"));
                  i++;
              }
              if(classIds!=null && classIds.length>0)
                reqMap.put("classes",classIds);
            }else if(user.getRole()==AppConstants.STUDENT_PIPE_ROLE){//学管处角色
                Integer colleageId = user.getColleageId();
                reqMap.put("colleageId",colleageId);//只查看自己负责的学院
                reqMap.put("approved",2);//只查看，需要学管处 审批的列表
            }

        }else if("2".equals(viewType)){//按照班级查看
           /* if(user.getRole().equals(AppConstants.INSTRUCTOR__ROLE)) {//辅导员角色
                reqMap.put("approved","-1");//只查看，未审批的请假列表
            }else if (user.getRole()==AppConstants.STUDENT_PIPE_ROLE){//学管处角色
                reqMap.put("approved",2);//只查看，需要学管处 审批的列表
            }*/
        }
        reqMap.put("orderBy","create_time");
        List<Leave> leaveList = leaveDao.getLeaveList(reqMap);
        converLeaveList(leaveList);
        return leaveList;
    }

    @Override
    public Map studentLeaveStatistics(Map queryMap) {
//        queryMap.put("startTime", startTime);
//        queryMap.put("endTime", endTime);
//        queryMap.put("statisticsType", statisticsType);
//        queryMap.put("userId",userId);
          int leaveDays = 0;
          int firstClassTimes = 0;
          int secondClassTimes = 0;
          int thirdClassTimes = 0;
          int fourthClassTimes = 0;
          int courseCount =0;
          Map resultMap = new HashMap();
          String statisticsType = (String)queryMap.get("statisticsType");
          if(StringUtils.isNotEmpty(statisticsType)){
              if("0".equals(statisticsType)){//只统计天数请假的
                  leaveDays = leaveDao.statisticsLeaveDays(queryMap);

              }
              List<String> courseList = new ArrayList<String>();
              if("1".equals(statisticsType)){//1  1+2 1+4 1+8
                  courseList.add("1");
                  courseList.add("3");
                  courseList.add("5");
                  courseList.add("9");
              }
              if("2".equals(statisticsType)){//2  2+1 2+4 2+8
                  courseList.add("2");
                  courseList.add("3");
                  courseList.add("6");
                  courseList.add("10");
              }
              if("4".equals(statisticsType)){//1+4  2+4 4 4+8
                  courseList.add("5");
                  courseList.add("6");
                  courseList.add("4");
                  courseList.add("12");
              }
              if("8".equals(statisticsType)){//1+8  2+8 4+8 8
                  courseList.add("9");
                  courseList.add("10");
                  courseList.add("12");
                  courseList.add("8");
              }
              queryMap.put("courseList",courseList);
              courseCount = leaveDao.statisticsLeaveCourseTimes(queryMap);
              if("1".equals(statisticsType)){//1  1+2 1+4 1+8
                  firstClassTimes=courseCount;
              }
              if("2".equals(statisticsType)){//2  2+1 2+4 2+8
                  secondClassTimes=courseCount;
              }
              if("4".equals(statisticsType)){//1+4  2+4 4 4+8
                  thirdClassTimes=courseCount;
              }
              if("8".equals(statisticsType)){//1+8  2+8 4+8 8
                  fourthClassTimes=courseCount;
              }
          }else{//没有具体的统计类型，则统计所有
              //统计天数
              leaveDays = leaveDao.statisticsLeaveDays(queryMap);
              List<String> courseList = new ArrayList<String>();
              queryMap.put("courseList",courseList);
              //统计第一节课
              courseList.add("1");
              courseList.add("3");
              courseList.add("5");
              courseList.add("9");
              courseCount = leaveDao.statisticsLeaveCourseTimes(queryMap);
              firstClassTimes=courseCount;
              courseList.clear();
              //统计第二节课
            courseList.add("2");
            courseList.add("3");
            courseList.add("6");
            courseList.add("10");
            courseCount = leaveDao.statisticsLeaveCourseTimes(queryMap);
            secondClassTimes=courseCount;
            courseList.clear();
              //统计第三节课
            courseList.add("5");
            courseList.add("6");
            courseList.add("4");
            courseList.add("12");
            courseCount = leaveDao.statisticsLeaveCourseTimes(queryMap);
            thirdClassTimes=courseCount;
            courseList.clear();
              //统计第四节课
            courseList.add("9");
            courseList.add("10");
            courseList.add("12");
            courseList.add("8");
            courseCount = leaveDao.statisticsLeaveCourseTimes(queryMap);
            fourthClassTimes=courseCount;
            courseList.clear();
          }
          resultMap.put("leaveDays",leaveDays);
          resultMap.put("firstClassTimes",firstClassTimes);
          resultMap.put("secondClassTimes",secondClassTimes);
          resultMap.put("thirdClassTimes",thirdClassTimes);
          resultMap.put("fourthClassTimes",fourthClassTimes);
        return resultMap;
    }

    @Override
    public List<Map> teacherGetLeaveStatistics(Map leaveQueryMap, String userId) {
        List<String> classIdList = new ArrayList<String>();
        //分页查询
        String pageNum = (String) leaveQueryMap.get("pageNum");
        String pageSize = (String) leaveQueryMap.get("pageSize");
        Integer start = (Integer.parseInt(pageNum)-1)*(Integer.parseInt(pageSize));
        Integer end =start + (Integer.parseInt(pageSize));
        leaveQueryMap.put("start",start);
        leaveQueryMap.put("end",end);

        Map userQueryMap = new HashMap();
        userQueryMap.put("userId", userId);
        User user  = redisInstance.getUserInfo(userId);
        int statisticType = (Integer) leaveQueryMap.get("statisticType");//请假类型  1：班级  2 时间  3学号
        int role = user.getRole();
        if(AppConstants.INSTRUCTOR__ROLE.intValue()==role) {//辅导员角色
            if(statisticType!=1){//按照班级统计，则 班级 是 给定的，所以辅导员此处不用再去查询自己负责的班级，以减少服务器压力
                List<Map> managedClassList = userService.getTeacherManagedClass(userQueryMap);
                if(managedClassList!=null){
                    for(Map map : managedClassList){
                        String classId = String.valueOf(map.get("class_id"));
                        classIdList.add(classId);
                    }
                    leaveQueryMap.put("classIdList",classIdList);
                }
            }
        }else if(AppConstants.STUDENT_PIPE_ROLE.intValue()==role){
            leaveQueryMap.put("colleageId", String.valueOf(user.getColleageId()));
        }
        List<Map> leaveStatisList = leaveDao.teacherGetLeaveStatistics(leaveQueryMap);

        return leaveStatisList;
    }

    @Override
    public int editLeave(Map reqMap) {
        String leaveType = (String) reqMap.get("leaveType");//请假类型
        reqMap.put("updateTime", DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
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
        int count = leaveDao.editLeave(reqMap);
        return count;
    }

    @Override
    public List<Leave> getSickedLeaveList(Map reqMap) {
        //所有的销假，都是基于 老师已经审批的基础上

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
        if("1".equals(reqMap.get("viewType"))){//按照时间查看，此时必须先获得该老师负责的班级
            if(role!=AppConstants.STUDENT_ROLE){
                if(role==AppConstants.INSTRUCTOR__ROLE){//当前用户辅导员角色
                    reqMap.put("teacherId",user.getUserId());
                    List<Map> classesList = userDao.getClassesByTeacherId(reqMap);
                    reqMap.put("classList",classesList);
                }else if(role==AppConstants.STUDENT_PIPE_ROLE){//学管处角色
                    Integer colleageId = user.getColleageId();
                    reqMap.put("colleageId",colleageId);
                }
            }
        }

        leaveList  = leaveDao.getSickLeaveList(reqMap);
        converLeaveList(leaveList);
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
                    if(leave.getLeaveDate()!=null &&  StringUtils.isNotEmpty(leave.getLeaveDate())){
                        Date leave_date =DateUtils.parseDate(leave.getLeaveDate(), new String[]{"yyyy-MM-dd HH:mm:ss.S"});
                        String leave_dateStr =  DateFormatUtils.format(leave_date, "yyyy-MM-dd");
                        leave.setLeaveDate(leave_dateStr);
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
