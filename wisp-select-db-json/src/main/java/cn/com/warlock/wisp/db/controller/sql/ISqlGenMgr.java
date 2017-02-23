package cn.com.warlock.wisp.db.controller.sql;

public interface ISqlGenMgr {

    String genSql(String tableId, String key, String keyValue);
}
