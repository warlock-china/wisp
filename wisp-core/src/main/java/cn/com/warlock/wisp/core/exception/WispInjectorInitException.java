package cn.com.warlock.wisp.core.exception;

public class WispInjectorInitException extends WispException {

    private static final long serialVersionUID = 1L;

    public WispInjectorInitException() {
    }

    public WispInjectorInitException(String message) {
        super(message);
    }

    public WispInjectorInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public WispInjectorInitException(Throwable cause) {
        super(cause);
    }
}
