package cn.com.warlock.wisp.plugin.processor.mybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.plugin.processor.IWispProcessor;
import cn.com.warlock.wisp.core.plugin.router.IWispDataRouter;
import cn.com.warlock.wisp.core.support.annotation.PluginName;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.core.support.context.IWispContextAware;
import cn.com.warlock.wisp.plugin.processor.mybatis.service.MybatisMgr;
import cn.com.warlock.wisp.plugin.processor.mybatis.service.impl.MybatisMgrImpl;

@PluginName(name = "wisp-processor-mybatis")
public class WispMybatisProcessor implements IWispProcessor, IWispDataRouter, IWispContextAware {

    private IWispContext iWispContext = null;

    private MybatisMgr mybatisMgr = null;

    protected static final Logger LOGGER = LoggerFactory.getLogger(WispMybatisProcessor.class);

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
        if (mybatisMgr != null) {
            //mybatisMgr.doWork();
            LOGGER.info(entry.toString());
        }
    }

    @Override
    public void processInsert(MysqlEntryWrap entry) throws WispProcessorException {
        LOGGER.info(entry.toString());
    }

    @Override
    public void processDelete(MysqlEntryWrap entry) throws WispProcessorException {
        LOGGER.info(entry.toString());
    }

    @Override
    public void init() {

        // open/read the application context file
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");

        mybatisMgr = (MybatisMgr) ctx.getBean(MybatisMgrImpl.class);

    }

    @Override
    public void shutdown() {

    }
}
