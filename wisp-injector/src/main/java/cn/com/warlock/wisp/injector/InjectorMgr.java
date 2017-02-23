package cn.com.warlock.wisp.injector;

import java.util.List;

import cn.com.warlock.wisp.core.exception.WispInjectorException;
import cn.com.warlock.wisp.core.exception.WispInjectorInitException;
import cn.com.warlock.wisp.core.plugin.injector.IWispInjector;
import cn.com.warlock.wisp.core.plugin.injector.template.IInjectEventProcessCallback;
import cn.com.warlock.wisp.core.support.context.IWispContextAware;

public interface InjectorMgr extends IWispContextAware {

    /**
     * @return
     */
    List<IWispInjector> getInjectorPlugin();

    /**
     * 执行
     */
    void runInjector() throws WispInjectorException;

    /**
     *
     */
    void init() throws WispInjectorInitException;

    /**
     * @param injectEntryProcessCallback
     */
    void setupEventProcessCallback(IInjectEventProcessCallback injectEntryProcessCallback);

    void shutdown();

}
