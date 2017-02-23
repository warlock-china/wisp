package cn.com.warlock.wisp.db.controller.sql.impl;

import cn.com.warlock.wisp.db.controller.sql.ISqlGenMgr;

public class SqlGenMgr implements ISqlGenMgr {

    @Override
    public String genSql(String tableId, String key, String keyValue) {

        return String.format("select * from %s where %s=%s", tableId, key, keyValue);
    }
}
