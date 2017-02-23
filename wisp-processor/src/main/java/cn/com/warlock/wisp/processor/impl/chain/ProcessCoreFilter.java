package cn.com.warlock.wisp.processor.impl.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.dto.MysqlEntry;
import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.plugin.processor.IWispProcessor;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.IEntryFilter;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.IEntryFilterChain;

public class ProcessCoreFilter implements IEntryFilter, IWispProcessorAware {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ProcessCoreFilter.class);

    private IWispProcessor iwispProcessor;

    @Override
    public void doFilter(MysqlEntryWrap entry, IEntryFilterChain iEntryFilterChain) throws WispProcessorException {

        // real do
        MysqlEntry mysqlEntry = entry.getMysqlEntry();
        if (mysqlEntry.getEvent() == MysqlEntry.MYSQL_INSERT) {

            LOGGER.debug("run processor... {}  insert", iwispProcessor.getClass());

            iwispProcessor.processInsert(entry);

        } else if (mysqlEntry.getEvent() == MysqlEntry.MYSQL_UPDATE) {

            LOGGER.debug("run processor... {}  update", iwispProcessor.getClass());

            iwispProcessor.processUpdate(entry);

        } else if (mysqlEntry.getEvent() == MysqlEntry.MYSQL_DELETE) {

            LOGGER.debug("run processor... {} delete", iwispProcessor.getClass());

            iwispProcessor.processDelete(entry);
        }
    }

    @Override
    public void setupIWispProcessor(IWispProcessor iWispProcessor) {
        this.iwispProcessor = iWispProcessor;
    }
}
