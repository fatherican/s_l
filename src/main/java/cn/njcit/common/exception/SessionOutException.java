package cn.njcit.common.exception;

/**
 * Created by YK on 2014-07-03.
 */
public class SessionOutException extends  RuntimeException {
    private String message;
    public SessionOutException(){
        this.message="session过时";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
