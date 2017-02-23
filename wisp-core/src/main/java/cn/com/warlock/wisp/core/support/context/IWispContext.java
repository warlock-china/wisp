package cn.com.warlock.wisp.core.support.context;

import java.io.IOException;
import java.util.Set;

import cn.com.warlock.wisp.core.plugin.router.IWispDataRouter;
import cn.com.warlock.wisp.core.support.properties.IProperties;

public interface IWispContext extends IProperties {

    /**
     * 获取 injector plugin name
     *
     * @return
     */
    Set<String> getInjectorPluginName();

    /**
     * 获取 processor plugin name
     *
     * @return
     */
    Set<String> getProcessorPluginName();

    /**
     * 获取router plugin name
     *
     * @return
     */
    Set<String> getRouterPluginName();

    /**
     * 载入
     *
     * @throws IOException
     */
    void load() throws IOException;

    /**
     * processor 作为数据源
     *
     * @param processorName
     *
     * @return
     */
    IWispDataRouter getProcessorAsDataSource(String processorName);
}
