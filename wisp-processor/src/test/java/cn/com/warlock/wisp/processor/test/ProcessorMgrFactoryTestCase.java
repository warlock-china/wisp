package cn.com.warlock.wisp.processor.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import cn.com.warlock.wisp.core.dto.MysqlEntry;
import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.plugin.IPlugin;
import cn.com.warlock.wisp.core.plugin.processor.IWispProcessor;
import cn.com.warlock.wisp.core.support.context.WispContextFactory;
import cn.com.warlock.wisp.processor.IProcessorMgr;
import cn.com.warlock.wisp.processor.ProcessorMgrFactory;
import cn.com.warlock.wisp.processor.support.ProcessorConstants;
import cn.com.warlock.wisp.core.support.context.IWispContext;

public class ProcessorMgrFactoryTestCase {

    @Test
    public void test() {

        IProcessorMgr processorMgr = ProcessorMgrFactory.getDefaultProcessorMgr();

        Set<String> specifyPluginNames = new HashSet<>();
        specifyPluginNames.add("wisp-processor-empty");

        try {

            // set up context
            IWispContext wispContext = WispContextFactory.getDefaultWispContext();

            // load
            ((IPlugin) processorMgr).loadPlugin(ProcessorConstants.SCAN_PACK_PLUGIN_PROCESSOR, specifyPluginNames);

            // get
            List<IWispProcessor> wispProcessors = processorMgr.getProcessorPlugin();
            Assert.assertEquals(wispProcessors.size(), 1);

            // set up context
            processorMgr.setWispContext(wispContext);

            // init
            processorMgr.init();

            // run
            MysqlEntryWrap mysqlEntryWrap = new MysqlEntryWrap("topic", new MysqlEntry());
            processorMgr.runProcessor(mysqlEntryWrap);

            //
            processorMgr.shutdown();

        } catch (Exception e) {

            Assert.assertTrue(false);
        }
    }
}
