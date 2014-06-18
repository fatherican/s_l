package cn.njcit.common.util;

import cn.njcit.common.constants.AppConstants;

/**
 * Created by YK on 2014-06-15.
 */
public class CommonUtil {

    /**
     * 封装Ajax返回
     * @param returnCode   接口的返回码
     * @param data         返回的数据
     * @param errorMessage 如果是错误，错误信息描述
     * @return
     */
    public static String ajaxReturn(int returnCode,String data,String errorMessage){
        StringBuffer sb = new StringBuffer();
        sb.append("errorCode:"+returnCode+","+"data:"+data);
        if(errorMessage!=null){
            sb.append(",errMsg:"+errorMessage);
        }else {
            sb.append(",errMsg:''");
        }
        return sb.toString();
    }


}
