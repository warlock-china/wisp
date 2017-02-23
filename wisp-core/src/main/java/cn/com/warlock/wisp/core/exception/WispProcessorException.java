package cn.com.warlock.wisp.core.exception;

public class WispProcessorException extends WispException {

    private static final long serialVersionUID = 1L;

    public WispProcessorException() {
    }

    public WispProcessorException(String message) {
        super(message);
    }

    public WispProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public WispProcessorException(Throwable cause) {
        super(cause);
    }
}
