package cn.com.warlock.wisp.core.plugin.injector.template;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispInjectorException;

/**
 * entry process event template
 */
public interface IInjectEventProcessOperator {

    void processEntry(MysqlEntryWrap mysqlEntry) throws
            WispInjectorException;

    void shutdown();
}
