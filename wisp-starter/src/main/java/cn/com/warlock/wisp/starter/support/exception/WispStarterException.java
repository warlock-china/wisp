package cn.com.warlock.wisp.starter.support.exception;

import cn.com.warlock.wisp.core.exception.WispException;

public class WispStarterException extends WispException {
    public WispStarterException() {
    }

    public WispStarterException(String message) {
        super(message);
    }

    public WispStarterException(String message, Throwable cause) {
        super(message, cause);
    }

    public WispStarterException(Throwable cause) {
        super(cause);
    }
}
