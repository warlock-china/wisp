package cn.com.warlock.wisp.core.test.plugin.processor.support.filter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cn.com.warlock.wisp.core.dto.MysqlEntry;
import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.EntryFilterChainFactory;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.IEntryFilter;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.IEntryFilterChain;

public class EntryFilterChainFactoryTestCase {

    @Test
    public void test() {

        List<IEntryFilter> filters = new ArrayList<>();

        IEntryFilter iEntryFilter = new IEntryFilter() {
            @Override
            public void doFilter(MysqlEntryWrap entry, IEntryFilterChain iEntryFilterChain)
                    throws WispProcessorException {

                iEntryFilterChain.doFilter(entry);
            }
        };
        filters.add(iEntryFilter);

        IEntryFilterChain iEntryFilterChain = EntryFilterChainFactory.getEntryFilterChain(filters);
        try {

            iEntryFilterChain.doFilter(new MysqlEntryWrap("topic", new MysqlEntry()));

        } catch (WispProcessorException e) {
            Assert.assertTrue(false);
        }
    }
}
