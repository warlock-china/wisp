package cn.com.warlock.wisp.plugin.router.rest.support.server;

import cn.com.warlock.wisp.core.exception.WispRouterException;

public interface IWispRouterServer {

    void start(int port) throws WispRouterException;

    void stop() throws WispRouterException;

}
