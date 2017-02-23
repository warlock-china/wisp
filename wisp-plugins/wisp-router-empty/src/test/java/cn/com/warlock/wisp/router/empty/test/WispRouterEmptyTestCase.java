package cn.com.warlock.wisp.router.empty.test;

import org.junit.Assert;
import org.junit.Test;

import cn.com.warlock.wisp.core.exception.WispRouterException;
import cn.com.warlock.wisp.core.plugin.router.IWispRouter;
import cn.com.warlock.wisp.plugin.router.empty.WispRouterEmpty;

public class WispRouterEmptyTestCase {

    @Test
    public void test() {

        IWispRouter wispRouter = new WispRouterEmpty();

        try {

            wispRouter.init();

            wispRouter.start();

            wispRouter.shutdown();

        } catch (WispRouterException e) {

            Assert.assertTrue(false);
        }
    }
}
