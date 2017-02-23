package cn.com.warlock.wisp.starter.core.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.exception.WispInjectorException;
import cn.com.warlock.wisp.core.exception.WispPluginException;
import cn.com.warlock.wisp.core.exception.WispRouterException;
import cn.com.warlock.wisp.core.plugin.injector.IWispInjector;
import cn.com.warlock.wisp.core.plugin.router.IWispRouter;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.starter.core.IWispController;
import cn.com.warlock.wisp.starter.core.IPluginMgr;

public class WispControllerImpl implements IWispController {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WispControllerImpl.class);

    protected IPluginMgr pluginMgr;

    // injector
    protected IWispInjector canalInjector = null;

    // router
    protected IWispRouter wispRouter = null;

    //
    protected Thread thread = null;
    protected volatile boolean running = false;

    public void run(IWispContext iWispContext) {

        loadPlugin(iWispContext);

        runMain();
    }

    /**
     *
     */
    private void runMain() {

        final WispControllerThread wispControllerThread = new WispControllerThread(this);
        thread = new Thread(new Runnable() {
            public void run() {

                try {
                    wispControllerThread.run();

                } catch (Throwable e) {
                    LOGGER.error(e.toString(), e);
                    running = false;
                }
            }
        });

        thread.start();
        running = true;
    }

    private void loadPlugin(IWispContext wispProfile) {

        try {

            // load plugin
            pluginMgr = new PluginMgrIml();
            pluginMgr.loadPlugins(wispProfile);

        } catch (WispPluginException ex) {

            LOGGER.error(ex.toString());
        }
    }

    @Override
    public void shutdown() {

        if (!running) {
            return;
        }
        running = false;
        if (thread != null) {
            try {
                //thread.join();
                thread.join(1000);   // 等待1000ms
            } catch (InterruptedException e) {
                LOGGER.warn(e.toString());
            }
        }

        if (canalInjector != null) {
            try {
                canalInjector.shutdown();
            } catch (WispInjectorException e) {
                LOGGER.warn(e.toString());
            }
        }

        if (wispRouter != null) {
            try {
                wispRouter.shutdown();
            } catch (WispRouterException e) {
                LOGGER.warn(e.toString());
            }
        }
    }
}
