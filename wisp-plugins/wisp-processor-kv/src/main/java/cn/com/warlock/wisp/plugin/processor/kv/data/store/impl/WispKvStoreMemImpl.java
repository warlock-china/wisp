package cn.com.warlock.wisp.plugin.processor.kv.data.store.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.plugin.processor.kv.data.store.IWispKv;

public class WispKvStoreMemImpl implements IWispKv {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WispKvStoreMemImpl.class);

    ConcurrentMap<String, ConcurrentMap<String, String>> dataMap = new ConcurrentHashMap<>(100);

    public static Builder create() {
        return new Builder();
    }

    private WispKvStoreMemImpl() {
    }

    /**
     * Builder
     */
    public static final class Builder {

        private Builder() {
        }

        private void validate() {
        }

        /**
         */
        public IWispKv build() {
            return new WispKvStoreMemImpl();
        }
    }

    @Override
    public String get(String tableId, String key) throws WispProcessorException {

        if (dataMap.keySet().contains(tableId)) {
            return dataMap.get(tableId).get(key);
        } else {

            return null;
        }
    }

    @Override
    public void put(String tableId, String key, String value) throws WispProcessorException {

        if (dataMap.keySet().contains(tableId)) {

            dataMap.get(tableId).put(key, value);

        } else {

            ConcurrentMap<String, String> currentMap = new ConcurrentHashMap<String, String>();
            currentMap.put(key, value);
            dataMap.put(tableId, currentMap);
        }
    }

    @Override
    public void delete(String tableId, String key) throws WispProcessorException {
        if (dataMap.keySet().contains(tableId)) {
            dataMap.get(tableId).remove(key);
        }
    }

    @Override
    public void shutdown() {

        dataMap.clear();
    }

}
