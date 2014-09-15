package cn.njcit.common.constants;

import java.util.Properties;

public class AppConstants {

    /* ************访问请求状态的返回码************/
    public static final int  SERVER_ERROR=500;//系统错误

    public static final int SUCCESS=200;//访问成功

    public static final int OTHER_ERROR=400;//参数错误、等等

    public static final int SESSION_ERROR=300;//参数错误、等等

    /* *************系统总的居中角色，学生、辅导员、学管处**********/
    public static final Integer STUDENT_ROLE=0;//学生角色

    public static final Integer INSTRUCTOR__ROLE=1;//辅导员角色

    public static final Integer STUDENT_PIPE_ROLE=2;//学管处角色

    public static final Integer ADMIN_ROLE=3;//超级管理员角色



    /* *************请假的类型（课程、天次）************************************/
    public static final Integer LEAVE_CLASS=0;//课程请假

    public static final Integer LEAVE_DAY=1;//天次请假





    /**
     * 项目的相关的配置信息
     */
    public static Properties appConfig;

    /**
     * 加载应用的相关的配置
     *
     */
    public static void loadAppConfig(Properties props) {
        appConfig = props;
    }

}
