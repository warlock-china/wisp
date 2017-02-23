package cn.com.warlock.wisp.db.config;

import lombok.Data;

@Data
public class TableConfig {

    String driverClass;

    String dbName;

    String dbUrl;

    String userName;

    String password;

    String tableName;

    String initSql;

    String identify;

    String keyId;

    public void genIdentify() {
        this.identify = this.dbName + "." + this.tableName;
    }
}
