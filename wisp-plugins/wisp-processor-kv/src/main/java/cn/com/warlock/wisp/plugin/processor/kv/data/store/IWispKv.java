package cn.com.warlock.wisp.plugin.processor.kv.data.store;

import cn.com.warlock.wisp.core.exception.WispProcessorException;

public interface IWispKv {

    String get(String tableId, String key) throws WispProcessorException;

    void put(String tableId, String key, String value) throws WispProcessorException;

    void delete(String tableId, String key) throws WispProcessorException;

    void shutdown();

}
