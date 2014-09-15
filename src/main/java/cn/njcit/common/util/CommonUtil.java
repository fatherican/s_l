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

    /**
     * 封装Ajax返回
     * @param data         返回的数据
     * @return
     */
    public static Map ajaxSuccess(Object data){
        Map map = new HashMap();
        map.put("code","200");
        map.put("data",data);
        map.put("msg","");
        return map;
    }


    /**
     * 封装Ajax返回
     * @param data         返回的数据
     * @return
     */
    public static Map ajaxFail(Object data,String message){
        Map map = new HashMap();
        map.put("code","500");
        map.put("data",data);
        map.put("msg",message==null?"":message);
        return map;
    }





    public static Map reurnDataTable(int total ,Object data,String error){
        Map map = new HashMap();
        map.put("recordsTotal",total);
        map.put("recordsFiltered",total);
        map.put("data",data);
        if(error!=null){
            map.put("error",error);
        }
        return map;
    }
}
