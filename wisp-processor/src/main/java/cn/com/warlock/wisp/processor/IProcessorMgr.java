package cn.com.warlock.wisp.processor;

import java.util.List;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.exception.WispProcessorInitException;
import cn.com.warlock.wisp.core.plugin.processor.IWispProcessor;
import cn.com.warlock.wisp.core.support.context.IWispContextAware;

public interface IProcessorMgr extends IWispContextAware {

    List<IWispProcessor> getProcessorPlugin();

    void runProcessor(MysqlEntryWrap entry) throws WispProcessorException;

    void init() throws WispProcessorInitException;

    void shutdown();
}

