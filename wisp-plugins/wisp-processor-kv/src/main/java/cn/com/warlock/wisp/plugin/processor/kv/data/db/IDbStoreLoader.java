package cn.com.warlock.wisp.plugin.processor.kv.data.db;

import cn.com.warlock.wisp.db.exception.WispSelectDbJsonInitException;
import cn.com.warlock.wisp.plugin.processor.kv.data.store.IWispKv;
import cn.com.warlock.wisp.plugin.processor.kv.data.support.WispKvConfig;

public interface IDbStoreLoader {

    void init(WispKvConfig wispKvConfig) throws WispSelectDbJsonInitException;

    void loadInitData(IWispKv iWispKv) throws WispSelectDbJsonInitException;

    String getTableKey(String tableId);

    String executeRowSql(String tableId, String keyValue);
}
