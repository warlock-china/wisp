package cn.com.warlock.wisp.processor.impl.chain;

import cn.com.warlock.wisp.core.plugin.processor.IWispProcessor;

public interface IWispProcessorAware {

    void setupIWispProcessor(IWispProcessor iWispProcessor);
}
