package cn.com.warlock.wisp.starter.core;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispException;
import cn.com.warlock.wisp.core.exception.WispInjectorException;
import cn.com.warlock.wisp.core.exception.WispPluginException;
import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.exception.WispRouterException;
import cn.com.warlock.wisp.core.plugin.injector.template.IInjectEventProcessCallback;
import cn.com.warlock.wisp.core.support.context.IWispContext;

public interface IPluginMgr {

    // 载入插件
    void loadPlugins(IWispContext wispProfile) throws WispPluginException;

    /**
     *
     */
    void init(IInjectEventProcessCallback injectEntryProcessCallback) throws WispException;

    /**
     * runner
     *
     * @throws WispInjectorException
     */
    void runInjector() throws WispInjectorException;

    void runProcessor(MysqlEntryWrap entry) throws WispProcessorException;

    void runRouter() throws WispRouterException;

    /**
     * stop
     */
    void stopInjector();

    void stopProcessor();

    void stopRouter();
}
