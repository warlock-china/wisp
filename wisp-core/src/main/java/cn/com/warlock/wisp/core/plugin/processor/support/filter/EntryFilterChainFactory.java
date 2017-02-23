package cn.com.warlock.wisp.core.plugin.processor.support.filter;

import java.util.List;

import cn.com.warlock.wisp.core.plugin.processor.support.filter.impl.EntryFilterChainImpl;

public class EntryFilterChainFactory {

    public static IEntryFilterChain getEntryFilterChain(List<IEntryFilter> filters) {
        return new EntryFilterChainImpl(filters);
    }

}
