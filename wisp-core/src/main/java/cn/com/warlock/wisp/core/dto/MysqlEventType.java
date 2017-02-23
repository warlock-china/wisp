package cn.com.warlock.wisp.core.dto;

/**
 * event type
 */
public enum MysqlEventType {

    UPDATE('u'), INSERT('i'), DELETE('d');
    private char type;

    MysqlEventType(char type) {
        this.type = type;
    }

    public char getType() {
        return type;
    }
}
