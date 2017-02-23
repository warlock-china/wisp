package cn.com.warlock.wisp.core.plugin.processor.support;

import cn.com.warlock.wisp.core.dto.MysqlEntry;
import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;

public class TableTopicUtil {

    public static String getTableId(String db, String table) {

        return String.format(db + "." + table);
    }

    public static String getTableId(MysqlEntryWrap entry) {

        MysqlEntry mysqlEntry = entry.getMysqlEntry();
        String table = mysqlEntry.getTable();
        String db = mysqlEntry.getDb();

        return getTableId(db, table);
    }
}
