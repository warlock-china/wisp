package cn.com.warlock.wisp.core.plugin.injector;

import cn.com.warlock.wisp.core.exception.WispInjectorException;
import cn.com.warlock.wisp.core.exception.WispInjectorInitException;

/**
 * data input injector
 *
 */
public interface IWispInjector {

    void init() throws WispInjectorInitException;

    void run() throws WispInjectorException;

    void shutdown() throws WispInjectorException;
}
