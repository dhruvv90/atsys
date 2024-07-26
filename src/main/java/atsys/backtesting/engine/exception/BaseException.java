package atsys.backtesting.engine.exception;

public class BaseException extends Exception{
    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(){
    }

    public BaseException(String message){
        super(message);
    }
}
