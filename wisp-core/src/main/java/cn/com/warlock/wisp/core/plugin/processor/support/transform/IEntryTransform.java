package cn.com.warlock.wisp.core.plugin.processor.support.transform;

import cn.com.warlock.wisp.core.dto.MysqlEntry;

public interface IEntryTransform {

    TransformResult entry2Json(MysqlEntry entry, String tableKey);
}
