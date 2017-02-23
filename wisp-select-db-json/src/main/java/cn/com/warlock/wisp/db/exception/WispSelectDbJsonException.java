package cn.com.warlock.wisp.db.exception;

public class WispSelectDbJsonException extends Exception {

    private static final long serialVersionUID = 1L;

    public WispSelectDbJsonException() {
    }

    public WispSelectDbJsonException(String message) {
        super(message);
    }

    public WispSelectDbJsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public WispSelectDbJsonException(Throwable cause) {
        super(cause);
    }
}
