package cn.com.warlock.wisp.core.plugin.router;

import cn.com.warlock.wisp.core.exception.WispRouterException;

public interface IWispRouter {

    void start() throws WispRouterException;

    void init() throws WispRouterException;

    void shutdown() throws WispRouterException;
}
