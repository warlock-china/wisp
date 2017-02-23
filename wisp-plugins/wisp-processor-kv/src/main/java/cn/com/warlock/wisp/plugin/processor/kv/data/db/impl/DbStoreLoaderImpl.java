package cn.com.warlock.wisp.plugin.processor.kv.data.db.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.db.DbFetchControllerFactory;
import cn.com.warlock.wisp.db.IDbFetchController;
import cn.com.warlock.wisp.db.exception.WispSelectDbJsonInitException;
import cn.com.warlock.wisp.plugin.processor.kv.data.db.IDbStoreLoader;
import cn.com.warlock.wisp.plugin.processor.kv.data.db.TableKey;
import cn.com.warlock.wisp.plugin.processor.kv.data.store.IWispKv;
import cn.com.warlock.wisp.plugin.processor.kv.data.support.WispKvConfig;

public class DbStoreLoaderImpl implements IDbStoreLoader {

    protected final Logger LOGGER = LoggerFactory.getLogger(DbStoreLoaderImpl.class);

    protected IDbFetchController iDbFetchController = DbFetchControllerFactory.getDefaultDbController();

    protected TableKey tableKey = new TableKey();

    @Override
    public void init(WispKvConfig wispKvConfig) throws WispSelectDbJsonInitException {

        iDbFetchController.init(wispKvConfig.getDbLoaderFilePath());
    }

    @Override
    public void loadInitData(IWispKv iWispKv) throws WispSelectDbJsonInitException {

        Map<String, Map<String, String>> dataInitMap = iDbFetchController.getInitDbKv();

        for (String tableId : dataInitMap.keySet()) {

            Map<String, String> tableKvMap = dataInitMap.get(tableId);

            for (String key : tableKvMap.keySet()) {

                try {

                    iWispKv.put(tableId, key, tableKvMap.get(key));

                } catch (WispProcessorException e) {

                    LOGGER.error("cannot put {} {} {}", tableId, key, tableKvMap.get(key), e);
                }
            }

            String key = iDbFetchController.getTableKey(tableId);
            tableKey.setTableKey(tableId, key);

            LOGGER.info("tableId: {} , TableKey {} loads ok", tableId, tableKey);
        }
    }

    @Override
    public String getTableKey(String tableId) {

        return tableKey.getTableKey(tableId);
    }

    @Override
    public String executeRowSql(String tableId, String keyValue) {

        return iDbFetchController.getRowByExecuteSql(tableId, keyValue);
    }
}
