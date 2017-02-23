package cn.com.warlock.wisp.core.test.plugin.injector.template;

import org.junit.Assert;
import org.junit.Test;

import cn.com.warlock.wisp.core.dto.MysqlEntry;
import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispInjectorException;
import cn.com.warlock.wisp.core.plugin.injector.template.IInjectEventProcessCallback;
import cn.com.warlock.wisp.core.plugin.injector.template.IInjectEventProcessOperator;
import cn.com.warlock.wisp.core.plugin.injector.template.InjectorEventProcessTemplate;

public class InjectorEventProcessTemplateTestCase {

    @Test
    public void test() {

        IInjectEventProcessOperator injectEventProcessOperator = new InjectorEventProcessTemplate(
                new IInjectEventProcessCallback() {

                    @Override
                    public void
                    processMysqlEntry(
                            MysqlEntryWrap mysqlEntry)
                            throws
                            WispInjectorException {

                    }

                    @Override
                    public void shutdown() {

                    }
                });

        MysqlEntryWrap mysqlEntryWrap = new MysqlEntryWrap("topic", new MysqlEntry());

        try {

            injectEventProcessOperator.processEntry(mysqlEntryWrap);

            injectEventProcessOperator.shutdown();

        } catch (WispInjectorException e) {

            Assert.assertTrue(false);
        }
    }
}
