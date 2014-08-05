package cn.njcit.common.util;

import cn.njcit.common.constants.AppConstants;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YK on 2014-06-15.
 */
public class CommonUtil {

    /**
     * 封装Ajax返回
     * @param returnCode   接口的返回码
     * @param data         返回的数据
     * @param message 如果是错误，错误信息描述
     * @return
     */
    public static String ajaxReturn(int returnCode,Object data,String message){
        Map map = new HashMap();
        map.put("code",returnCode);
        map.put("data",data);
        map.put("msg",message==null?"":message);
        return JSON.toJSONString(map);
    }


}
