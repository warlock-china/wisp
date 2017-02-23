package cn.com.warlock.wisp.plugin.processor.empty;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.plugin.processor.IWispProcessor;
import cn.com.warlock.wisp.core.plugin.router.IWispDataRouter;
import cn.com.warlock.wisp.core.support.annotation.PluginName;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.core.support.context.IWispContextAware;

@PluginName(name = "wisp-processor-empty")
public class WispEmptyProcessor implements IWispProcessor, IWispDataRouter, IWispContextAware {

    private IWispContext iWispContext = null;

    @Override
    public void setWispContext(IWispContext iWispContext) {
        this.iWispContext = iWispContext;
    }

    @Override
    public String get(String tableId, String key) {
        return null;
    }

    @Override
    public void processUpdate(MysqlEntryWrap entry) throws WispProcessorException {

    }

    @Override
    public void processInsert(MysqlEntryWrap entry) throws WispProcessorException {

    }

    @Override
    public void processDelete(MysqlEntryWrap entry) throws WispProcessorException {

    }

    @Override
    public void init() {

    }

    @Override
    public void shutdown() {

    }
}
