package cn.com.warlock.wisp.plugin.processor.empty.test;

import org.junit.Assert;
import org.junit.Test;

import cn.com.warlock.wisp.core.dto.MysqlEntry;
import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.plugin.processor.IWispProcessor;
import cn.com.warlock.wisp.plugin.processor.empty.WispEmptyProcessor;

public class WispEmptyProcessorTestCase {

    @Test
    public void test() {

        IWispProcessor wispEmptyProcessor = new WispEmptyProcessor();

        try {

            wispEmptyProcessor.init();

            MysqlEntryWrap mysqlEntryWrap = new MysqlEntryWrap("topic", new MysqlEntry());

            wispEmptyProcessor.processDelete(mysqlEntryWrap);

            wispEmptyProcessor.processInsert(mysqlEntryWrap);
            wispEmptyProcessor.processUpdate(mysqlEntryWrap);

            wispEmptyProcessor.shutdown();

        } catch (WispProcessorException e) {

            Assert.assertTrue(false);
        }

    }
}
