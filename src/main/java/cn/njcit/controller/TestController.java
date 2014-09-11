package cn.njcit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YK on 2014-06-09.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("dt")
    public String toPage(HttpServletRequest reqeust,HttpServletResponse response){
        return "dt";
    }

    @RequestMapping("data")
    public @ResponseBody Map getData(HttpServletRequest reqeust,HttpServletResponse response){
        ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
        for(int i=0;i<24;i++){
            Map<String,String> item = new HashMap<String,String>();
            item.put("name","yangkai");
            item.put("age","24");
            item.put("gendle","f");
            list.add(item);
        }
        Map dataMap = new HashMap();
        dataMap.put("data",list);

        return dataMap;
    }



    @RequestMapping("index")
    public String index(HttpServletRequest reqeust,HttpServletResponse response){

        return "/web/index";
    }


    @RequestMapping("loginPage")
    public String loginPage(HttpServletRequest reqeust,HttpServletResponse response){

        return "/web/login";
    }
}
