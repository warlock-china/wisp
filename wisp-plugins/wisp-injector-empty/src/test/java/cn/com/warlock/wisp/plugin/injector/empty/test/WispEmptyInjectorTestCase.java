package cn.com.warlock.wisp.plugin.injector.empty.test;

import org.junit.Assert;
import org.junit.Test;

import cn.com.warlock.wisp.core.exception.WispInjectorException;
import cn.com.warlock.wisp.core.exception.WispInjectorInitException;
import cn.com.warlock.wisp.core.plugin.injector.IWispInjector;
import cn.com.warlock.wisp.plugin.injector.empty.WispEmptyInjector;

public class WispEmptyInjectorTestCase {

    @Test
    public void test() {

        IWispInjector wispInjector = new WispEmptyInjector();

        try {

            wispInjector.init();

            wispInjector.run();

            wispInjector.shutdown();

        } catch (WispInjectorInitException e) {

            Assert.assertTrue(false);

        } catch (WispInjectorException e) {

            Assert.assertTrue(false);
        }

    }
}
