package cn.njcit.core.redis;

import cn.njcit.common.constants.AppConstants;
import cn.njcit.common.exception.SessionOutException;
import cn.njcit.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by YK on 2014-06-30.
 */
@Component
public class RedisInstance {

    @Autowired
    private RedisTemplate redisTemplate;



//    private RedisInstance(){};
//
//
//    private static RedisInstance redisInstance;
//
//    public static RedisInstance getInstance(){
//        if(redisInstance==null){
//            redisInstance = new RedisInstance();
//        }
//        return redisInstance;
//    }


    public void saveUserInfo(String userId,User user){
        ValueOperations valueOps = redisTemplate.opsForValue();
        if(redisTemplate.hasKey(user.getUserId())){
            redisTemplate.delete(user.getUserId());
        }
        valueOps.set(userId, user);
        redisTemplate.expire(userId, Long.parseLong(AppConstants.appConfig.getProperty("redis.expireTime")), TimeUnit.MILLISECONDS);
    }

    public User getUserInfo(String userId){
        ValueOperations valueOps = redisTemplate.opsForValue();
        User user = null;
        System.out.println(valueOps.get(userId));
        user = (User)valueOps.get(userId);
        if(user==null){
            throw new SessionOutException();
        }
        redisTemplate.expire(userId, Long.parseLong(AppConstants.appConfig.getProperty("redis.expireTime")), TimeUnit.MILLISECONDS);
        return user;
    }



}
