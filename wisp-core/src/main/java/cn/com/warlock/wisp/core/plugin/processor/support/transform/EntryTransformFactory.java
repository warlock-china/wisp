package cn.com.warlock.wisp.core.plugin.processor.support.transform;

import cn.com.warlock.wisp.core.plugin.processor.support.transform.impl.DeleteEntryTransformImpl;
import cn.com.warlock.wisp.core.plugin.processor.support.transform.impl.InsertEntryTransformImpl;
import cn.com.warlock.wisp.core.plugin.processor.support.transform.impl.UpdateEntryTransformImpl;

public class EntryTransformFactory {

    public static IEntryTransform getInsertTransform() {
        return new InsertEntryTransformImpl();
    }

    public static IEntryTransform getDeleteTransform() {
        return new DeleteEntryTransformImpl();
    }

    public static IEntryTransform getUpdateTransform() {
        return new UpdateEntryTransformImpl();
    }
}
