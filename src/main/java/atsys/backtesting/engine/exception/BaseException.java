package atsys.backtesting.engine.exception;

public class BaseException extends Exception{
    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(){
        super();
    }
}
