package cn.com.warlock.wisp.router;

import cn.com.warlock.wisp.router.impl.RouterMgrImpl;

public class RouterMgrFactory {

    public static IRouterMgr getDefaultRouterMgr() {
        return new RouterMgrImpl();
    }
}
