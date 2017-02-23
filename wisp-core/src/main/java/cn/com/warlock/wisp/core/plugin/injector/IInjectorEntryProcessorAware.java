package cn.com.warlock.wisp.core.plugin.injector;

import cn.com.warlock.wisp.core.plugin.injector.template.InjectorEventProcessTemplate;

/**
 * entry process aware
 */
public interface IInjectorEntryProcessorAware {

    void setupProcessEntry(InjectorEventProcessTemplate injectorEntryProcessTemplate);
}
