package cn.com.warlock.wisp.injector.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.exception.WispInjectorException;
import cn.com.warlock.wisp.core.exception.WispInjectorInitException;
import cn.com.warlock.wisp.core.plugin.IPlugin;
import cn.com.warlock.wisp.core.plugin.injector.IWispInjector;
import cn.com.warlock.wisp.core.plugin.injector.IInjectorEntryProcessorAware;
import cn.com.warlock.wisp.core.plugin.injector.template.IInjectEventProcessCallback;
import cn.com.warlock.wisp.core.plugin.injector.template.InjectorEventProcessTemplate;
import cn.com.warlock.wisp.core.support.annotation.PluginName;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.core.support.context.IWispContextAware;
import cn.com.warlock.wisp.core.support.reflection.ReflectionUtil;
import cn.com.warlock.wisp.injector.InjectorMgr;

public class InjectorMgrImpl implements InjectorMgr, IPlugin {

    protected static final Logger LOGGER = LoggerFactory.getLogger(InjectorMgrImpl.class);

    // injector
    private Map<String, IWispInjector> innerCanalInjectors = new LinkedHashMap<String, IWispInjector>(10);

    //
    private IWispInjector firstInjector = null;

    // context
    private IWispContext iWispContext = null;

    /**
     * load plugins
     *
     * @param scanPack
     * @param specifyPluginNames
     */
    @Override
    public void loadPlugin(String scanPack, Set<String> specifyPluginNames) {

        Reflections reflections = ReflectionUtil.getReflection(scanPack);
        Set<Class<? extends IWispInjector>> canalInjectors = reflections.getSubTypesOf(IWispInjector
                .class);

        boolean isFirst = true;
        for (Class<? extends IWispInjector> canalInjector : canalInjectors) {

            String pluginName = canalInjector.getAnnotation(PluginName.class).name();

            if (!specifyPluginNames.contains(pluginName)) {
                continue;
            }

            LOGGER.info("loading injector: {} - {}", pluginName, canalInjector.toString());

            try {
                Class<IWispInjector> canalInjectorClass = (Class<IWispInjector>) canalInjector;

                innerCanalInjectors.put(pluginName, canalInjectorClass.newInstance());

                if (isFirst) {
                    firstInjector = innerCanalInjectors.get(pluginName);
                    isFirst = false;
                }

            } catch (Exception e) {
                LOGGER.error(e.toString());
            }
        }
    }

    /**
     * @return
     */
    @Override
    public List<IWispInjector> getInjectorPlugin() {

        List<IWispInjector> iCanalInjectors = new ArrayList<>(10);

        for (String processorName : innerCanalInjectors.keySet()) {

            iCanalInjectors.add(innerCanalInjectors.get(processorName));
        }

        return iCanalInjectors;
    }

    /**
     * @param
     */
    @Override
    public void runInjector() throws WispInjectorException {

        if (firstInjector != null) {

            // run
            firstInjector.run();

            return;
        }

        LOGGER.info("there are empty injectors in the container.. system will stop.. ");
    }

    @Override
    public void init() throws WispInjectorInitException {

        if (firstInjector != null) {

            if (firstInjector instanceof IWispContextAware) {
                ((IWispContextAware) firstInjector).setWispContext(iWispContext);
            }

            // init
            firstInjector.init();
        }
    }

    @Override
    public void setupEventProcessCallback(IInjectEventProcessCallback injectEntryProcessCallback) {

        if (firstInjector != null) {

            if (firstInjector instanceof IInjectorEntryProcessorAware) {
                ((IInjectorEntryProcessorAware) firstInjector).setupProcessEntry(new InjectorEventProcessTemplate
                        (injectEntryProcessCallback));
            }

        }
    }

    @Override
    public void shutdown() {

        if (firstInjector != null) {
            try {
                firstInjector.shutdown();
            } catch (WispInjectorException e) {
                LOGGER.warn(e.toString());
            }
        }
    }

    @Override
    public void setWispContext(IWispContext iWispContext) {
        this.iWispContext = iWispContext;
    }
}
