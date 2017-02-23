package cn.com.warlock.wisp.core.exception;

public class WispRouterException extends WispException {

    private static final long serialVersionUID = 1L;

    public WispRouterException() {
    }

    public WispRouterException(String message) {
        super(message);
    }

    public WispRouterException(String message, Throwable cause) {
        super(message, cause);
    }

    public WispRouterException(Throwable cause) {
        super(cause);
    }
}
