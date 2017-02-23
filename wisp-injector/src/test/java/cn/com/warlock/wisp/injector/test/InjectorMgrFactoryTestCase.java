package cn.com.warlock.wisp.injector.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispInjectorException;
import cn.com.warlock.wisp.core.plugin.IPlugin;
import cn.com.warlock.wisp.core.plugin.injector.IWispInjector;
import cn.com.warlock.wisp.core.plugin.injector.template.IInjectEventProcessCallback;
import cn.com.warlock.wisp.core.support.context.WispContextFactory;
import cn.com.warlock.wisp.injector.InjectorMgr;
import cn.com.warlock.wisp.injector.InjectorMgrFactory;
import cn.com.warlock.wisp.injector.support.InjectConstants;
import cn.com.warlock.wisp.core.support.context.IWispContext;

public class InjectorMgrFactoryTestCase {

    @Test
    public void test() {

        InjectorMgr injectorMgr = InjectorMgrFactory.getDefaultInjectorMgr();

        Set<String> specifyPluginNames = new HashSet<>();
        specifyPluginNames.add("wisp-injector-empty");

        try {

            // set up context
            IWispContext wispContext = WispContextFactory.getDefaultWispContext();

            // load
            ((IPlugin) injectorMgr).loadPlugin(InjectConstants.SCAN_PACK_PLUGIN_INJECT, specifyPluginNames);

            // get
            List<IWispInjector> wispInjectors = injectorMgr.getInjectorPlugin();
            Assert.assertEquals(wispInjectors.size(), 1);

            // set up context
            injectorMgr.setWispContext(wispContext);

            // set up callback
            injectorMgr.setupEventProcessCallback(new IInjectEventProcessCallback() {
                @Override
                public void processMysqlEntry(MysqlEntryWrap mysqlEntry) throws WispInjectorException {

                }

                @Override
                public void shutdown() {

                }
            });

            // init
            injectorMgr.init();

            // run
            injectorMgr.runInjector();

            //
            injectorMgr.shutdown();

        } catch (Exception e) {

            Assert.assertTrue(false);
        }
    }
}
