package cn.com.warlock.wisp.core.plugin.injector.template;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispInjectorException;

public interface IInjectEventProcessCallback {

    void processMysqlEntry(MysqlEntryWrap mysqlEntry) throws WispInjectorException;

    void shutdown();
}
