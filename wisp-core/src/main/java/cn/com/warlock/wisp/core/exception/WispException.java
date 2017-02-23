package cn.com.warlock.wisp.core.exception;

public class WispException extends Exception {

    private static final long serialVersionUID = 1L;

    public WispException() {
    }

    public WispException(String message) {
        super(message);
    }

    public WispException(String message, Throwable cause) {
        super(message, cause);
    }

    public WispException(Throwable cause) {
        super(cause);
    }
}
