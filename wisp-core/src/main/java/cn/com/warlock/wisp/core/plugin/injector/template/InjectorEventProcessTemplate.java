package cn.com.warlock.wisp.core.plugin.injector.template;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispInjectorException;

/**
 * entry process template
 *
 */
public class InjectorEventProcessTemplate implements IInjectEventProcessOperator {

    protected IInjectEventProcessCallback injectEventProcessCallback;

    public InjectorEventProcessTemplate(IInjectEventProcessCallback injectEventProcessCallback) {
        this.injectEventProcessCallback = injectEventProcessCallback;
    }

    @Override
    public void processEntry(MysqlEntryWrap mysqlEntry) throws
            WispInjectorException {

        if (injectEventProcessCallback != null) {
            injectEventProcessCallback.processMysqlEntry(mysqlEntry);
        }
    }

    @Override
    public void shutdown() {

        if (injectEventProcessCallback != null) {
            injectEventProcessCallback.shutdown();
        }
    }
}
