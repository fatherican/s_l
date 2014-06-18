package cn.njcit.common.constants;

import java.util.Properties;

public class AppConstants {

    /* ************访问请求状态的返回码************/
    public static final int  SERVER_ERROR=500;//系统错误

    public static final int SUCCESS=200;//访问成功

    public static final int OTHER_ERROR=400;//参数错误、等等

    /* *************系统总的居中角色，学生、辅导员、学管处**********/
    public static final Integer STUDENT_ROLE=0;

    public static final Integer INSTRUCTOR__ROLE=1;

    public static final Integer STUDENT_PIPE_ROLE=2;

    /**
     * ubgw-core相关的配置信息
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
