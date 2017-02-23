package cn.com.warlock.wisp.plugin.injector.empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.exception.WispInjectorException;
import cn.com.warlock.wisp.core.exception.WispInjectorInitException;
import cn.com.warlock.wisp.core.plugin.injector.IWispInjector;
import cn.com.warlock.wisp.core.plugin.injector.IInjectorEntryProcessorAware;
import cn.com.warlock.wisp.core.plugin.injector.template.InjectorEventProcessTemplate;
import cn.com.warlock.wisp.core.support.annotation.PluginName;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.core.support.context.IWispContextAware;

@PluginName(name = "wisp-injector-empty")
public class WispEmptyInjector implements IWispInjector, IInjectorEntryProcessorAware, IWispContextAware {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WispEmptyInjector.class);

    //
    private IWispContext iWispContext = null;

    @Override
    public void setWispContext(IWispContext iWispContext) {
        this.iWispContext = iWispContext;
    }

    @Override
    public void init() throws WispInjectorInitException {

    }

    @Override
    public void run() throws WispInjectorException {

    }

    @Override
    public void shutdown() throws WispInjectorException {

    }

    @Override
    public void setupProcessEntry(InjectorEventProcessTemplate injectorEntryProcessTemplate) {

    }
}
