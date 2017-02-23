package cn.com.warlock.wisp.core.plugin;

import java.util.Set;

import cn.com.warlock.wisp.core.exception.WispPluginException;

public interface IPlugin {

    // load
    void loadPlugin(String scanPack, Set<String> specifyPluginNames) throws WispPluginException;
}
