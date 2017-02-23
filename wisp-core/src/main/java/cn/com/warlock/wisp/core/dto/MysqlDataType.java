package cn.com.warlock.wisp.core.dto;

/**
 * mysql data type
 *
 */
public enum MysqlDataType {
    TINYINT("tinyint"), BOOL("bool"), SMALLINT("smallint"), MEDIUMINT(
            "mediumint"), INT("int"), INTEGER("integer"), BIGINT("bigint"), DECIMAL(
            "decimal"), FLOAT("float"), DOUBLE("double"), VARCHAR("varchar"), TIMESTAMP(
            "timestamp"), DATE("date");

    private String typeName;

    MysqlDataType(String typename) {
        this.typeName = typename;
    }

    public String getTypeName() {
        return typeName;
    }
}
