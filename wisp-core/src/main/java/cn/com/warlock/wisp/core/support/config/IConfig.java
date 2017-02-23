package cn.com.warlock.wisp.core.support.config;

import cn.com.warlock.wisp.core.support.context.IWispContext;

public interface IConfig {

    void init(IWispContext iWispContext) throws Exception;

    void init() throws Exception;
}
