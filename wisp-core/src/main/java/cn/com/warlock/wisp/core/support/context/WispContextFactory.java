package cn.com.warlock.wisp.core.support.context;

import cn.com.warlock.wisp.core.support.context.impl.WispContextImpl;

public class WispContextFactory {

    public static IWispContext getDefaultWispContext() {
        return new WispContextImpl();
    }
}
