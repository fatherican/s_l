package cn.njcit.common.exception;

/**
 * Created by YK on 2014-06-15.
 */
public class ParameterException extends Exception{
    private String message;



    public  ParameterException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
