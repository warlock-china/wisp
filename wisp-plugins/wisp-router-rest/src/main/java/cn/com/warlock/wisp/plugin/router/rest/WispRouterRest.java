package cn.com.warlock.wisp.plugin.router.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.exception.WispRouterException;
import cn.com.warlock.wisp.core.plugin.router.IWispDataRouter;
import cn.com.warlock.wisp.core.plugin.router.IWispRouter;
import cn.com.warlock.wisp.core.support.annotation.PluginName;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.core.support.context.IWispContextAware;
import cn.com.warlock.wisp.plugin.router.rest.support.config.RouterRestConfig;
import cn.com.warlock.wisp.plugin.router.rest.support.server.WispDataGetter;
import cn.com.warlock.wisp.plugin.router.rest.support.server.WispRouterServerFactory;
import cn.com.warlock.wisp.plugin.router.rest.support.server.IWispRouterServer;

@PluginName(name = "wisp-router-rest")
public class WispRouterRest implements IWispRouter, IWispContextAware {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WispRouterRest.class);

    // config
    protected RouterRestConfig routerRestConfig = new RouterRestConfig();

    // context
    protected IWispContext wispContext;

    // data source
    protected IWispDataRouter iWispDataRouter = null;

    //
    protected IWispRouterServer iWispRouterServer = WispRouterServerFactory.getDefaultRouterServer();

    protected boolean isInit = false;

    @Override
    public void start() throws WispRouterException {

        if (isInit) {

            if (iWispRouterServer != null) {

                iWispRouterServer.start(routerRestConfig.getServerPort());
            }
        }
    }

    @Override
    public void init() throws WispRouterException {

        try {
            if (wispContext != null) {

                // config
                routerRestConfig.init(wispContext);

                // get data source
                this.iWispDataRouter = wispContext.getProcessorAsDataSource(routerRestConfig.getDataSource());
                WispDataGetter.setupIWispDataRouter(iWispDataRouter);

                isInit = true;
            }
        } catch (Exception e) {
            throw new WispRouterException(e);
        }

    }

    @Override
    public void shutdown() throws WispRouterException {

        if (isInit) {
            iWispRouterServer.stop();
        }
        LOGGER.info(WispRouterRest.class.toString() + " stops gracefully");
    }

    @Override
    public void setWispContext(IWispContext iWispContext) {
        this.wispContext = iWispContext;
    }
}
