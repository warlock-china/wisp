package cn.com.warlock.wisp.plugin.processor.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.plugin.processor.IWispProcessor;
import cn.com.warlock.wisp.core.plugin.processor.support.TableTopicUtil;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.filters.EntryTimeFilter;
import cn.com.warlock.wisp.core.plugin.processor.support.transform.EntryTransformFactory;
import cn.com.warlock.wisp.core.plugin.processor.support.transform.IEntryTransform;
import cn.com.warlock.wisp.core.plugin.processor.support.transform.TransformResult;
import cn.com.warlock.wisp.core.plugin.router.IWispDataRouter;
import cn.com.warlock.wisp.core.support.annotation.EntryFilterList;
import cn.com.warlock.wisp.core.support.annotation.PluginName;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.core.support.context.IWispContextAware;
import cn.com.warlock.wisp.plugin.processor.kv.data.WispKvInstance;

@PluginName(name = "wisp-processor-kv")
@EntryFilterList(classes = {EntryTimeFilter.class})
public class WispKvProcessor implements IWispProcessor, IWispDataRouter, IWispContextAware {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WispKvProcessor.class);

    //
    private IWispContext iWispContext = null;

    // transform
    private IEntryTransform insertTransform = EntryTransformFactory.getInsertTransform();
    private IEntryTransform updateTransform = EntryTransformFactory.getUpdateTransform();
    private IEntryTransform deleteTransform = EntryTransformFactory.getDeleteTransform();

    @Override
    public void processUpdate(MysqlEntryWrap entry) throws WispProcessorException {

        LOGGER.info(entry.toString());

        String tableId = TableTopicUtil.getTableId(entry);

        String tableKey = WispKvInstance.getTableKey(tableId);

        if (tableKey.equals("")) {
            LOGGER.error("cannot find tableKey for tableId {} with insert op.", tableId);
        } else {

            TransformResult transformResult = updateTransform.entry2Json(entry.getMysqlEntry(), tableKey);
            WispKvInstance.put(tableId, transformResult.getKey(), transformResult.getValue());
        }

    }

    @Override
    public void processInsert(MysqlEntryWrap entry) throws WispProcessorException {

        LOGGER.info(entry.toString());

        String tableId = TableTopicUtil.getTableId(entry);

        String tableKey = WispKvInstance.getTableKey(tableId);

        if (tableKey.equals("")) {
            LOGGER.error("cannot find tableKey for tableId {} with update op. ", tableId);
        } else {

            TransformResult transformResult = insertTransform.entry2Json(entry.getMysqlEntry(), tableKey);
            WispKvInstance.put(tableId, transformResult.getKey(), transformResult.getValue());
        }

    }

    @Override
    public void processDelete(MysqlEntryWrap entry) throws WispProcessorException {

        // not process
        LOGGER.info(entry.toString());

        String tableId = TableTopicUtil.getTableId(entry);

        String tableKey = WispKvInstance.getTableKey(tableId);

        if (tableKey.equals("")) {
            LOGGER.error("cannot find tableKey for tableId {} with update op. ", tableId);
        } else {

            TransformResult transformResult = insertTransform.entry2Json(entry.getMysqlEntry(), tableKey);
            WispKvInstance.delete(tableId, transformResult.getKey());
        }
    }

    /**
     * 初始化
     */
    @Override
    public void init() {

        LOGGER.info("start to init {}", WispKvProcessor.class.toString());
        WispKvInstance.init(iWispContext);
    }

    @Override
    public void shutdown() {

        WispKvInstance.shutdown();
        LOGGER.info(WispKvProcessor.class.toString() + " stops gracefully");
    }

    /**
     * @param tableId
     * @param key
     *
     * @return
     */
    @Override
    public String get(String tableId, String key) {

        return WispKvInstance.get(tableId, key);
    }

    @Override
    public void setWispContext(IWispContext iWispContext) {
        this.iWispContext = iWispContext;
    }
}
