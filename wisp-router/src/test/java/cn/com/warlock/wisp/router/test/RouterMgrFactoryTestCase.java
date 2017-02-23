package cn.com.warlock.wisp.router.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import cn.com.warlock.wisp.core.plugin.IPlugin;
import cn.com.warlock.wisp.core.plugin.router.IWispRouter;
import cn.com.warlock.wisp.core.support.context.WispContextFactory;
import cn.com.warlock.wisp.router.IRouterMgr;
import cn.com.warlock.wisp.router.RouterMgrFactory;
import cn.com.warlock.wisp.router.support.RouterConstants;
import cn.com.warlock.wisp.core.support.context.IWispContext;

public class RouterMgrFactoryTestCase {

    @Test
    public void test() {

        IRouterMgr routerMgr = RouterMgrFactory.getDefaultRouterMgr();

        Set<String> specifyPluginNames = new HashSet<>();
        specifyPluginNames.add("wisp-router-empty");

        try {

            // set up context
            IWispContext wispContext = WispContextFactory.getDefaultWispContext();

            // load
            ((IPlugin) routerMgr).loadPlugin(RouterConstants.SCAN_PACK_PLUGIN_ROUTER, specifyPluginNames);

            // get
            List<IWispRouter> wispRouters = routerMgr.getRouterPlugin();
            Assert.assertEquals(wispRouters.size(), 1);

            // set up context
            routerMgr.setWispContext(wispContext);

            // init
            routerMgr.init();

            // run
            routerMgr.runRouter();

            //
            routerMgr.shutdown();

        } catch (Exception e) {

            Assert.assertTrue(false);
        }
    }
}
