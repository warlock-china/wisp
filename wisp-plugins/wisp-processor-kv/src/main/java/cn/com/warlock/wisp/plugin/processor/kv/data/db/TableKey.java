package cn.com.warlock.wisp.plugin.processor.kv.data.db;

import java.util.HashMap;

/**
 * 存储了每个table的key name, 如果没有设定,那默认就是id
 *
 */
public class TableKey {

    private HashMap<String, String> myTableKey = new HashMap<>();

    public void setTableKey(String table, String key) {

        myTableKey.put(table, key);
    }

    public String getTableKey(String table) {

        if (myTableKey.keySet().contains(table)) {
            return myTableKey.get(table);
        }

        return "id";
    }
}
