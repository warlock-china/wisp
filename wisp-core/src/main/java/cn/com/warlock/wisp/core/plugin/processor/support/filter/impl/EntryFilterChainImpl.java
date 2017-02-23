package cn.com.warlock.wisp.core.plugin.processor.support.filter.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.IEntryFilter;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.IEntryFilterChain;

public class EntryFilterChainImpl implements IEntryFilterChain {

    protected static final Logger LOGGER = LoggerFactory.getLogger(EntryFilterChainImpl.class);

    final List<IEntryFilter> chain;
    int curFilter = 0;

    public EntryFilterChainImpl(List<IEntryFilter> filters) {
        chain = filters;
    }

    @Override
    public void doFilter(MysqlEntryWrap entry) throws WispProcessorException {

        // pass to next filter
        if (curFilter < chain.size()) {

            IEntryFilter entryFilter = chain.get(curFilter);

            curFilter += 1;

            LOGGER.debug("call filter " + entryFilter.getClass().toString());

            entryFilter.doFilter(entry, this);
        }

        curFilter = 0;
    }
}
