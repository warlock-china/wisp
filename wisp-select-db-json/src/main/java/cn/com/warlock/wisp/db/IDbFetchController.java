package cn.com.warlock.wisp.db;

import java.util.Map;

import cn.com.warlock.wisp.db.exception.WispSelectDbJsonInitException;

public interface IDbFetchController {

    // first key: table id
    // second key: the key of table
    Map<String, Map<String, String>> getInitDbKv();

    // init
    void init(String configFilePath) throws WispSelectDbJsonInitException;

    //
    String getTableKey(String tableId);

    //
    String getRowByExecuteSql(String tableId, String keyValue);
}
