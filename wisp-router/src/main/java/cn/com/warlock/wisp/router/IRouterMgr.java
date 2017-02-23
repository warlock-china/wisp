package cn.com.warlock.wisp.router;

import java.util.List;

import cn.com.warlock.wisp.core.exception.WispRouterException;
import cn.com.warlock.wisp.core.plugin.router.IWispRouter;
import cn.com.warlock.wisp.core.support.context.IWispContextAware;

public interface IRouterMgr extends IWispContextAware {

    /**
     * @return
     */
    List<IWispRouter> getRouterPlugin();

    /**
     * 执行
     */
    void runRouter() throws WispRouterException;

    /**
     *
     */
    void init() throws WispRouterException;

    void shutdown();
}
