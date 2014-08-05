package cn.njcit.common.exception;

import cn.njcit.common.constants.AppConstants;
import cn.njcit.common.util.CommonUtil;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YK on 2014-07-18.
 */
public class ExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse response, Object handler, Exception ex) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ex", ex);
        // 根据不同错误转向不同页面
        String message = null;
        String returnedMessage="";
        if(ex instanceof ServiceException) {
            message=ex.getMessage();
            returnedMessage=CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", message);
        }else if(ex instanceof ParameterException) {
            message=ex.getMessage();
            returnedMessage=CommonUtil.ajaxReturn(AppConstants.OTHER_ERROR, "", message);
        } else if(ex instanceof SessionOutException) {
            message=ex.getMessage();
            returnedMessage=CommonUtil.ajaxReturn(AppConstants.SESSION_ERROR, "", message);
        }else{
            ex.printStackTrace();
        }
        ModelAndView mav = new ModelAndView("/exception/exception");
        mav.addObject("data",returnedMessage);
        return mav;
    }
}
