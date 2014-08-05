package cn.njcit.controller.redis;

import cn.njcit.core.redis.RedisInstance;
import cn.njcit.domain.user.User;
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

    @Autowired
    private RedisInstance redisInstance;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/set")
    public @ResponseBody String addValue(HttpServletRequest request,HttpServletResponse response){
//        redisInstance.saveUserInfo("1","jjjjj");

        User user = new User();
        user.setName("不该");
        redisTemplate.opsForValue().set("1",user);


        return null;
    }

    @RequestMapping("/get")
    public @ResponseBody String getValue(HttpServletRequest request,HttpServletResponse response){
//        String str = redisInstance.getUserInfo("1");
//        System.out.println(str);
        User user = (User) redisTemplate.opsForValue().get("1");
        System.out.println("-----"+user.getName()+"-------");
        return null;
    }




}
