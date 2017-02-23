package cn.com.warlock.wisp.core.exception;

public class WispPluginException extends WispException {

    private static final long serialVersionUID = 1L;

    public WispPluginException() {
    }

    public WispPluginException(String message) {
        super(message);
    }

    public WispPluginException(String message, Throwable cause) {
        super(message, cause);
    }

    public WispPluginException(Throwable cause) {
        super(cause);
    }
}
