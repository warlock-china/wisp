package cn.com.warlock.wisp.processor.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispPluginException;
import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.exception.WispProcessorInitException;
import cn.com.warlock.wisp.core.plugin.IPlugin;
import cn.com.warlock.wisp.core.plugin.processor.IWispProcessor;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.EntryFilterChainFactory;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.IEntryFilter;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.IEntryFilterChain;
import cn.com.warlock.wisp.core.support.annotation.EntryFilterList;
import cn.com.warlock.wisp.core.support.annotation.PluginName;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.core.support.context.IWispContextAware;
import cn.com.warlock.wisp.core.support.context.IDataSourceAware;
import cn.com.warlock.wisp.core.support.reflection.ReflectionUtil;
import cn.com.warlock.wisp.processor.IProcessorMgr;
import cn.com.warlock.wisp.processor.impl.chain.IWispProcessorAware;
import cn.com.warlock.wisp.processor.impl.chain.ProcessCoreFilter;

public class ProcessorMgrImpl implements IProcessorMgr, IPlugin {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ProcessorMgrImpl.class);

    private Map<String, IWispProcessor> innerCanalProcessors = new LinkedHashMap<String, IWispProcessor>(10);

    // entry filter
    private Map<String, IEntryFilterChain> innerCanalEntryFilterMap = new HashMap<>();

    //
    private List<IWispProcessor> iWispProcessors = new ArrayList<>(10);

    // context
    private IWispContext iWispContext = null;

    @Override
    public void loadPlugin(String scanPack, Set<String> specifyPluginNames) throws WispPluginException {

        Reflections reflections = ReflectionUtil.getReflection(scanPack);
        Set<Class<? extends IWispProcessor>> canalProcessors = reflections.getSubTypesOf(IWispProcessor
                .class);

        for (Class<? extends IWispProcessor> canalProcessor : canalProcessors) {

            String pluginName = canalProcessor.getAnnotation(PluginName.class).name();

            if (!specifyPluginNames.contains(pluginName)) {
                continue;
            }

            LOGGER.info("loading processor: {} - {}", pluginName, canalProcessor.toString());

            try {

                Class<IWispProcessor> canalProcessorClass = (Class<IWispProcessor>) canalProcessor;

                IWispProcessor iWispProcessor = canalProcessorClass.newInstance();

                // load filter
                loadEntryFilterList(pluginName, canalProcessorClass, iWispProcessor);

                innerCanalProcessors.put(pluginName, iWispProcessor);
            } catch (Exception e) {
                LOGGER.error(e.toString(), e);
            }
        }

        for (String processorName : innerCanalProcessors.keySet()) {

            iWispProcessors.add(innerCanalProcessors.get(processorName));
        }

        if (iWispContext instanceof IDataSourceAware) {
            ((IDataSourceAware) iWispContext).setDataSource(this.innerCanalProcessors);
        }
    }

    /**
     * 载入filter
     *
     * @param canalProcessorClass
     */
    private void loadEntryFilterList(String pluginName, Class<IWispProcessor> canalProcessorClass, IWispProcessor
            iWispProcessor) {

        List<Class<?>> classList = new ArrayList<>();

        // get filter list
        EntryFilterList entryFilterList = canalProcessorClass.getAnnotation(EntryFilterList.class);

        if (entryFilterList != null) {
            Class<?>[] classes = entryFilterList.classes();
            classList = new ArrayList<>(Arrays.asList(classes));
        }

        // add core
        classList.add(ProcessCoreFilter.class);

        List<IEntryFilter> iEntryFilters = new ArrayList<>();

        for (Class<?> curClass : classList) {

            try {

                IEntryFilter iEntryFilter = (IEntryFilter) curClass.newInstance();

                // core filter
                if (iEntryFilter instanceof IWispProcessorAware) {
                    ((IWispProcessorAware) iEntryFilter).setupIWispProcessor(iWispProcessor);
                }

                iEntryFilters.add(iEntryFilter);

            } catch (Exception e) {
                LOGGER.error(e.toString(), e);
            }
        }

        IEntryFilterChain iEntryFilterChain = EntryFilterChainFactory.getEntryFilterChain(iEntryFilters);
        innerCanalEntryFilterMap.put(pluginName, iEntryFilterChain);
    }

    @Override
    public List<IWispProcessor> getProcessorPlugin() {
        return iWispProcessors;
    }

    /**
     * @param entry
     */
    @Override
    public void runProcessor(MysqlEntryWrap entry) throws WispProcessorException {

        for (String pluginName : innerCanalEntryFilterMap.keySet()) {

            // filter
            IEntryFilterChain iEntryFilterChain = innerCanalEntryFilterMap.get(pluginName);
            iEntryFilterChain.doFilter(entry);
        }
    }

    @Override
    public void init() throws WispProcessorInitException {

        for (IWispProcessor iWispProcessor : iWispProcessors) {

            if (iWispProcessor instanceof IWispContextAware) {
                ((IWispContextAware) iWispProcessor).setWispContext(iWispContext);
            }

            iWispProcessor.init();
        }
    }

    @Override
    public void shutdown() {

        for (IWispProcessor iWispProcessor : iWispProcessors) {

            iWispProcessor.shutdown();
        }
    }

    @Override
    public void setWispContext(IWispContext iWispContext) {
        this.iWispContext = iWispContext;

        if (iWispContext instanceof IDataSourceAware) {
            ((IDataSourceAware) iWispContext).setDataSource(this.innerCanalProcessors);
        }
    }
}
