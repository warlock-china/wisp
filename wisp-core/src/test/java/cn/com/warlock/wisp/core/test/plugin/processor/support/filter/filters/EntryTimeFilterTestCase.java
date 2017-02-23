package cn.com.warlock.wisp.core.test.plugin.processor.support.filter.filters;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.StringContains;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import cn.com.warlock.wisp.core.dto.MysqlEntry;
import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.EntryFilterChainFactory;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.IEntryFilter;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.IEntryFilterChain;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.filters.EntryTimeFilter;
import cn.com.warlock.wisp.test.log.logback.LogbackCapturingAppender;

public class EntryTimeFilterTestCase {

    @After
    public void cleanUp() {
        LogbackCapturingAppender.Factory.cleanUp();
    }

    @Test
    public void testHasIgnore() {

        // Given logger
        LogbackCapturingAppender capturing = LogbackCapturingAppender.Factory.weaveInto(EntryTimeFilter.getLOGGER());

        IEntryFilter entryFilter = new EntryTimeFilter();

        List<IEntryFilter> filters = new ArrayList<>();
        filters.add(entryFilter);
        filters.add(entryFilter);
        IEntryFilterChain entryFilterChain = EntryFilterChainFactory.getEntryFilterChain(filters);

        try {

            MysqlEntry mysqlEntry = new MysqlEntry();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            long currentTime = timestamp.getTime();
            mysqlEntry.setTime(currentTime - 5000 * 4);
            entryFilterChain.doFilter(new MysqlEntryWrap("topic", mysqlEntry));

        } catch (WispProcessorException e) {
            Assert.assertTrue(false);
        }

        MatcherAssert.assertThat(capturing.getCapturedLogMessage(), StringContains.containsString("ignore entry"));

    }

    @Test
    public void testOk() {

        // Given logger
        LogbackCapturingAppender capturing = LogbackCapturingAppender.Factory.weaveInto(EntryTimeFilter.getLOGGER());

        IEntryFilter entryFilter = new EntryTimeFilter();

        List<IEntryFilter> filters = new ArrayList<>();
        filters.add(entryFilter);
        filters.add(entryFilter);
        IEntryFilterChain entryFilterChain = EntryFilterChainFactory.getEntryFilterChain(filters);

        try {

            MysqlEntry mysqlEntry = new MysqlEntry();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            long currentTime = timestamp.getTime();
            mysqlEntry.setTime(currentTime - 1000 * 2);
            entryFilterChain.doFilter(new MysqlEntryWrap("topic", mysqlEntry));

        } catch (WispProcessorException e) {
            Assert.assertTrue(false);
        }

        Assert.assertEquals(capturing.getCapturedLogMessage(), null);

    }
}
