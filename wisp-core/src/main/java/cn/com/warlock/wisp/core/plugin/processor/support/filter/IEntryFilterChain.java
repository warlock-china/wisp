package cn.com.warlock.wisp.core.plugin.processor.support.filter;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispProcessorException;

public interface IEntryFilterChain {

    void doFilter(MysqlEntryWrap entry) throws WispProcessorException;

}
