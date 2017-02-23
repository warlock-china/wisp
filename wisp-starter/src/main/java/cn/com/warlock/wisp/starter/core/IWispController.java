package cn.com.warlock.wisp.starter.core;

import cn.com.warlock.wisp.core.support.context.IWispContext;

public interface IWispController {

    void run(IWispContext wispProfile);

    void shutdown();
}
