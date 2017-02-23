package cn.com.warlock.wisp.plugin.router.empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.exception.WispRouterException;
import cn.com.warlock.wisp.core.plugin.router.IWispRouter;
import cn.com.warlock.wisp.core.support.annotation.PluginName;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.core.support.context.IWispContextAware;

@PluginName(name = "wisp-router-empty")
public class WispRouterEmpty implements IWispRouter, IWispContextAware {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WispRouterEmpty.class);

    // context
    protected IWispContext wispContext;

    @Override
    public void setWispContext(IWispContext iWispContext) {
        this.wispContext = iWispContext;
    }

    @Override
    public void start() throws WispRouterException {

    }

    @Override
    public void init() throws WispRouterException {

    }

    @Override
    public void shutdown() throws WispRouterException {

    }
}
