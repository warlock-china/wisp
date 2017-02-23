package cn.com.warlock.wisp.db.exception;

public class WispSelectDbJsonInitException extends WispSelectDbJsonException {

    private static final long serialVersionUID = 1L;

    public WispSelectDbJsonInitException() {
    }

    public WispSelectDbJsonInitException(String message) {
        super(message);
    }

    public WispSelectDbJsonInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public WispSelectDbJsonInitException(Throwable cause) {
        super(cause);
    }
}
