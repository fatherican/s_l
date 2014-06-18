package cn.njcit.controller.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by YK on 2014-05-30.
 */
@RequestMapping("/redis")
@Controller
public class RedisController {
/*

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/set")
    public @ResponseBody String addValue(HttpServletRequest request,HttpServletResponse response){
       Integer num = redisTemplate.opsForValue().append("test111111","this is my test");



        return "aaaa"+num;
    }

    @RequestMapping("/get")
    public @ResponseBody String getValue(HttpServletRequest request,HttpServletResponse response){
        String str = (String) redisTemplate.opsForValue().get("test111111");


        return str;
    }

    @RequestMapping("/del")
    public @ResponseBody void delValue(HttpServletRequest request,HttpServletResponse response){
        redisTemplate.opsForValue().getOperations().delete("test111111");

    }
*/


}
