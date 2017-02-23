package cn.com.warlock.wisp.plugin.router.rest.support.server;

import cn.com.warlock.wisp.plugin.router.rest.support.server.impl.IWispRouterSeverImpl;

public class WispRouterServerFactory {

    public static IWispRouterServer getDefaultRouterServer() {
        return new IWispRouterSeverImpl();
    }
}
