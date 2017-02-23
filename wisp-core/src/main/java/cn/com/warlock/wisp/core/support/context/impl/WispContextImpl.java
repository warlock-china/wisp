package cn.com.warlock.wisp.core.support.context.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.plugin.processor.IWispProcessor;
import cn.com.warlock.wisp.core.plugin.router.IWispDataRouter;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.core.support.context.IDataSourceAware;

public class WispContextImpl implements IWispContext, IDataSourceAware {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WispContextImpl.class);

    private final Properties properties;

    // data source provider
    private Map<String, IWispProcessor> iWispProcessorMap;

    private String FILE_NAME = "wisp.properties";

    public WispContextImpl() {
        properties = new Properties();
    }

    @Override
    public Set<String> getInjectorPluginName() {

        String injectorStr = properties.getProperty("wisp.plugin.injector", "");
        Set<String> ret = new LinkedHashSet<>(10);
        String[] data = injectorStr.split(",");
        for (String item : data) {
            ret.add(item);
        }
        return ret;
    }

    @Override
    public Set<String> getProcessorPluginName() {
        String processorStr = properties.getProperty("wisp.plugin.processor", "");
        Set<String> ret = new LinkedHashSet<>(10);
        String[] data = processorStr.split(",");
        for (String item : data) {
            ret.add(item);
        }
        return ret;
    }

    @Override
    public Set<String> getRouterPluginName() {
        String processorStr = properties.getProperty("wisp.plugin.router", "");
        Set<String> ret = new LinkedHashSet<>(10);
        String[] data = processorStr.split(",");
        for (String item : data) {
            ret.add(item);
        }
        return ret;
    }

    @Override
    public void load() throws IOException {
        InputStream inputStream = WispContextImpl.class.getClassLoader().getResourceAsStream(FILE_NAME);
        properties.load(inputStream);
        LOGGER.info("loading sys config: {}", FILE_NAME);
    }

    /**
     * 获取某一个 processor 作为 router 的数据源
     *
     * @param processorName
     *
     * @return
     */
    @Override
    public IWispDataRouter getProcessorAsDataSource(String processorName) {

        if (processorName != null) {
            for (String iWispProcessorName : iWispProcessorMap.keySet()) {
                if (iWispProcessorName.equals(processorName)) {

                    IWispProcessor iWispProcessor = iWispProcessorMap.get(processorName);
                    if (iWispProcessor instanceof IWispDataRouter) {
                        return (IWispDataRouter) iWispProcessor;
                    }
                }
            }

            LOGGER.warn("cannot get any data source as rest input: {} ", processorName);
        }

        return null;
    }

    @Override
    public String getProperty(String item, String defaultValue) {
        return properties.getProperty(item, defaultValue);
    }

    @Override
    public String getProperty(String item) {
        return properties.getProperty(item);
    }

    /**
     * 设置数据源
     *
     * @param iWispProcessorMap
     */
    @Override
    public void setDataSource(Map<String, IWispProcessor> iWispProcessorMap) {
        this.iWispProcessorMap = iWispProcessorMap;
    }
}
