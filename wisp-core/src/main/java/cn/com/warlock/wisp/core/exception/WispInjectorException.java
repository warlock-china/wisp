package cn.com.warlock.wisp.core.exception;

public class WispInjectorException extends WispException {

    private static final long serialVersionUID = 1L;

    public WispInjectorException() {
    }

    public WispInjectorException(String message) {
        super(message);
    }

    public WispInjectorException(String message, Throwable cause) {
        super(message, cause);
    }

    public WispInjectorException(Throwable cause) {
        super(cause);
    }
}
