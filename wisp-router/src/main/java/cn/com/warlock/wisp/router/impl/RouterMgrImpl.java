package cn.com.warlock.wisp.router.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.exception.WispPluginException;
import cn.com.warlock.wisp.core.exception.WispRouterException;
import cn.com.warlock.wisp.core.plugin.IPlugin;
import cn.com.warlock.wisp.core.plugin.router.IWispRouter;
import cn.com.warlock.wisp.core.support.annotation.PluginName;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.core.support.context.IWispContextAware;
import cn.com.warlock.wisp.core.support.reflection.ReflectionUtil;
import cn.com.warlock.wisp.router.IRouterMgr;

public class RouterMgrImpl implements IRouterMgr, IPlugin {

    protected static final Logger LOGGER = LoggerFactory.getLogger(RouterMgrImpl.class);

    // injector
    private Map<String, IWispRouter> innerWispRouterMap = new LinkedHashMap<String, IWispRouter>(10);

    //
    private IWispRouter firstWispRouter = null;

    // context
    private IWispContext iWispContext = null;

    /**
     * @return
     */
    @Override
    public List<IWispRouter> getRouterPlugin() {
        List<IWispRouter> iWispRouters = new ArrayList<>(10);

        for (String processorName : innerWispRouterMap.keySet()) {

            iWispRouters.add(innerWispRouterMap.get(processorName));
        }

        return iWispRouters;
    }

    @Override
    public void runRouter() throws WispRouterException {

        if (firstWispRouter != null) {

            // run
            firstWispRouter.start();
        }
    }

    @Override
    public void init() throws WispRouterException {

        if (firstWispRouter != null) {

            if (firstWispRouter instanceof IWispContextAware) {
                ((IWispContextAware) firstWispRouter).setWispContext(iWispContext);
            }

            // init
            firstWispRouter.init();
        }
    }

    @Override
    public void shutdown() {

        if (firstWispRouter != null) {
            try {
                firstWispRouter.shutdown();
            } catch (WispRouterException e) {
                LOGGER.warn(e.toString());
            }
        }
    }

    @Override
    public void setWispContext(IWispContext iWispContext) {
        this.iWispContext = iWispContext;
    }

    /**
     * @param scanPack
     * @param specifyPluginNames
     *
     * @throws WispPluginException
     */
    @Override
    public void loadPlugin(String scanPack, Set<String> specifyPluginNames) throws WispPluginException {

        Reflections reflections = ReflectionUtil.getReflection(scanPack);
        Set<Class<? extends IWispRouter>> wispRouters = reflections.getSubTypesOf(IWispRouter
                .class);

        boolean isFirst = true;
        for (Class<? extends IWispRouter> canalInjector : wispRouters) {

            String pluginName = canalInjector.getAnnotation(PluginName.class).name();

            if (!specifyPluginNames.contains(pluginName)) {
                continue;
            }

            LOGGER.info("loading router: {} - {}", pluginName, canalInjector.toString());

            try {
                Class<IWispRouter> canalInjectorClass = (Class<IWispRouter>) canalInjector;

                innerWispRouterMap.put(pluginName, canalInjectorClass.newInstance());

                if (isFirst) {
                    firstWispRouter = innerWispRouterMap.get(pluginName);
                    isFirst = false;
                }

            } catch (Exception e) {
                LOGGER.error(e.toString());
            }
        }
    }
}
