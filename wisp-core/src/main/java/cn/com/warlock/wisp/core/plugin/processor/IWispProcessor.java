package cn.com.warlock.wisp.core.plugin.processor;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispProcessorException;

/**
 * 消息处理接口
 *
 */
public interface IWispProcessor {

    /**
     * update
     *
     * @param entry
     * @param
     */
    void processUpdate(MysqlEntryWrap entry) throws WispProcessorException;

    /**
     * insert
     *
     * @param entry
     * @param
     */
    void processInsert(MysqlEntryWrap entry) throws WispProcessorException;

    /**
     * delete
     *
     * @param entry
     * @param
     */
    void processDelete(MysqlEntryWrap entry) throws WispProcessorException;

    /**

     */
    void init();

    void shutdown();
}
