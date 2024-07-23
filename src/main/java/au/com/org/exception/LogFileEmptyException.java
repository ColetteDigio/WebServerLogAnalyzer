package au.com.org.exception;

public class LogFileEmptyException extends RuntimeException{
    public LogFileEmptyException(String message) {
        super(message);
    }
}

