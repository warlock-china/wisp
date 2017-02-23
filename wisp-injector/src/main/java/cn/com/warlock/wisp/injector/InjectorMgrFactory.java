package cn.com.warlock.wisp.injector;

import cn.com.warlock.wisp.injector.impl.InjectorMgrImpl;

public class InjectorMgrFactory {

    public static InjectorMgr getDefaultInjectorMgr() {
        return new InjectorMgrImpl();
    }
}
