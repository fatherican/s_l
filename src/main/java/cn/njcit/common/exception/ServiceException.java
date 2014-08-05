package cn.njcit.common.exception;

/**
 * Created by YK on 2014-06-15.
 */
public class ServiceException extends Exception{
    private String message;



    public ServiceException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
