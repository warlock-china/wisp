package cn.com.warlock.wisp.core.support.context;

import java.util.Map;

import cn.com.warlock.wisp.core.plugin.processor.IWispProcessor;

public interface IDataSourceAware {

    void setDataSource(Map<String, IWispProcessor> iWispProcessorMap);
}
