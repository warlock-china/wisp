package cn.com.warlock.wisp.core.exception;

public class WispProcessorInitException extends WispException {

    private static final long serialVersionUID = 1L;

    public WispProcessorInitException() {
    }

    public WispProcessorInitException(String message) {
        super(message);
    }

    public WispProcessorInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public WispProcessorInitException(Throwable cause) {
        super(cause);
    }
}
