package cn.com.warlock.wisp.starter.core.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispException;
import cn.com.warlock.wisp.core.exception.WispInjectorException;
import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.exception.WispRouterException;
import cn.com.warlock.wisp.core.plugin.injector.template.IInjectEventProcessCallback;

public class WispControllerThread implements Runnable {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WispControllerThread.class);

    protected WispControllerImpl wispController;

    protected boolean isInitOk = false;

    public WispControllerThread(WispControllerImpl wispController) {

        this.wispController = wispController;
    }

    @Override
    public void run() {

        // init injector and processor
        init();

        // run injector
        runInjector();

        // run router
        runRouter();
    }

    private void init() {

        try {

            // injector call back
            IInjectEventProcessCallback injectEntryProcessCallback = new IInjectEventProcessCallback() {

                @Override
                public void processMysqlEntry(MysqlEntryWrap mysqlEntry) throws WispInjectorException {

                    runProcessor(mysqlEntry);
                }

                @Override
                public void shutdown() {

                }
            };

            this.wispController.pluginMgr.init(injectEntryProcessCallback);

            //
            isInitOk = true;

            LOGGER.info("init injector and processor ok");

        } catch (WispException e) {

            LOGGER.error(e.toString(), e);
        }
    }

    /**
     * run injector
     */
    private void runInjector() {

        if (isInitOk) {

            try {
                this.wispController.pluginMgr.runInjector();
            } catch (WispInjectorException e) {
                LOGGER.error(e.toString());
            }
        }
    }

    /**
     * run router
     */
    private void runRouter() {

        if (isInitOk) {

            try {
                this.wispController.pluginMgr.runRouter();
            } catch (WispRouterException e) {
                LOGGER.error(e.toString());
            }
        }
    }

    /**
     * run processor
     */
    private void runProcessor(MysqlEntryWrap entry) {

        try {
            this.wispController.pluginMgr.runProcessor(entry);
        } catch (WispProcessorException e) {
            LOGGER.error(e.toString());
        }
    }

    /**
     * stop processor
     */
    private void shutdownProcessor() {

        this.wispController.pluginMgr.stopInjector();
    }
}
