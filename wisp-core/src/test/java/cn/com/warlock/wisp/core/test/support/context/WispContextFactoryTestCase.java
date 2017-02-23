package cn.com.warlock.wisp.core.test.support.context;

import java.io.IOException;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import cn.com.warlock.wisp.core.support.context.WispContextFactory;
import cn.com.warlock.wisp.core.support.context.IWispContext;

public class WispContextFactoryTestCase {

    @Test
    public void test() {

        IWispContext iWispContext = WispContextFactory.getDefaultWispContext();

        try {

            iWispContext.load();

            Set<String> injectorPluginNameSet = iWispContext.getInjectorPluginName();
            Assert.assertEquals(injectorPluginNameSet.toString(), "[wisp-injector-kafka]");

            Set<String> processorPluginNameSet = iWispContext.getProcessorPluginName();
            Assert.assertEquals(processorPluginNameSet.toString(), "[wisp-processor-kv]");

            Set<String> routerPluginNameSet = iWispContext.getRouterPluginName();
            Assert.assertEquals(routerPluginNameSet.toString(), "[wisp-router-rest]");

            String topics = iWispContext.getProperty("wisp.plugin.injector.topics");
            Assert.assertEquals(topics, "test,test2");

        } catch (IOException e) {

            Assert.assertTrue(false);
        }
    }
}
