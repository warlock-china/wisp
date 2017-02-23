package cn.com.warlock.wisp.starter.core.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispException;
import cn.com.warlock.wisp.core.exception.WispInjectorException;
import cn.com.warlock.wisp.core.exception.WispPluginException;
import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.exception.WispRouterException;
import cn.com.warlock.wisp.core.plugin.IPlugin;
import cn.com.warlock.wisp.core.plugin.injector.template.IInjectEventProcessCallback;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.injector.InjectorMgr;
import cn.com.warlock.wisp.injector.InjectorMgrFactory;
import cn.com.warlock.wisp.injector.support.InjectConstants;
import cn.com.warlock.wisp.processor.IProcessorMgr;
import cn.com.warlock.wisp.processor.ProcessorMgrFactory;
import cn.com.warlock.wisp.processor.support.ProcessorConstants;
import cn.com.warlock.wisp.router.IRouterMgr;
import cn.com.warlock.wisp.router.RouterMgrFactory;
import cn.com.warlock.wisp.router.support.RouterConstants;
import cn.com.warlock.wisp.starter.core.IPluginMgr;

public class PluginMgrIml implements IPluginMgr {

    protected static final Logger LOGGER = LoggerFactory.getLogger(PluginMgrIml.class);

    // injector mgr
    private InjectorMgr injectorMgr = InjectorMgrFactory.getDefaultInjectorMgr();

    // processors`
    private IProcessorMgr processorMgr = ProcessorMgrFactory.getDefaultProcessorMgr();

    // router`
    private IRouterMgr routerMgr = RouterMgrFactory.getDefaultRouterMgr();

    @Override
    public void loadPlugins(IWispContext iWispContext) throws WispPluginException {

        //
        // load injector
        //
        Set<String> userInjectors = iWispContext.getInjectorPluginName();
        if (injectorMgr instanceof IPlugin) {
            ((IPlugin) injectorMgr).loadPlugin(InjectConstants.SCAN_PACK_PLUGIN_INJECT, userInjectors);
        }
        injectorMgr.setWispContext(iWispContext);

        //
        //  load processor
        //
        Set<String> userProcessorNames = iWispContext.getProcessorPluginName();
        if (processorMgr instanceof IPlugin) {
            ((IPlugin) processorMgr).loadPlugin(ProcessorConstants.SCAN_PACK_PLUGIN_PROCESSOR, userProcessorNames);
        }
        processorMgr.setWispContext(iWispContext);

        //
        //  load router
        //
        Set<String> userRouterNames = iWispContext.getRouterPluginName();
        if (routerMgr instanceof IPlugin) {
            ((IPlugin) routerMgr).loadPlugin(RouterConstants.SCAN_PACK_PLUGIN_ROUTER, userRouterNames);
        }
        routerMgr.setWispContext(iWispContext);
    }

    /**
     *
     */
    public void init(IInjectEventProcessCallback injectEntryProcessCallback) throws WispException {

        // init injector
        injectorMgr.setupEventProcessCallback(injectEntryProcessCallback);
        injectorMgr.init();

        // init processor
        processorMgr.init();

        // init router
        routerMgr.init();
    }

    @Override
    public void runInjector() throws WispInjectorException {
        injectorMgr.runInjector();
    }

    @Override
    public void runProcessor(MysqlEntryWrap entry) throws WispProcessorException {
        processorMgr.runProcessor(entry);
    }

    @Override
    public void runRouter() throws WispRouterException {
        routerMgr.runRouter();
    }

    @Override
    public void stopInjector() {
        injectorMgr.shutdown();
    }

    @Override
    public void stopProcessor() {
        processorMgr.shutdown();
    }

    @Override
    public void stopRouter() {
        routerMgr.shutdown();
    }

}
