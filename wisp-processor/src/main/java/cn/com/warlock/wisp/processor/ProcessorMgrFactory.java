package cn.com.warlock.wisp.processor;

import cn.com.warlock.wisp.processor.impl.ProcessorMgrImpl;

public class ProcessorMgrFactory {

    public static IProcessorMgr getDefaultProcessorMgr() {
        return new ProcessorMgrImpl();
    }
}
