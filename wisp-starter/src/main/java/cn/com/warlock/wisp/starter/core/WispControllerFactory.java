package cn.com.warlock.wisp.starter.core;

import cn.com.warlock.wisp.starter.core.impl.WispControllerImpl;

public class WispControllerFactory {

    public static IWispController getDefaultController() {
        return new WispControllerImpl();
    }
}
