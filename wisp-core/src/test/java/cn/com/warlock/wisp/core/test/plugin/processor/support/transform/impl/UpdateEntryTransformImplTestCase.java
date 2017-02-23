package cn.com.warlock.wisp.core.test.plugin.processor.support.transform.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cn.com.warlock.wisp.core.dto.MysqlColumn;
import cn.com.warlock.wisp.core.dto.MysqlEntry;
import cn.com.warlock.wisp.core.plugin.processor.support.transform.EntryTransformFactory;
import cn.com.warlock.wisp.core.plugin.processor.support.transform.IEntryTransform;
import cn.com.warlock.wisp.core.plugin.processor.support.transform.TransformResult;

public class UpdateEntryTransformImplTestCase {

    @Test
    public void testOk() {

        IEntryTransform iEntryTransform = EntryTransformFactory.getUpdateTransform();

        MysqlEntry mysqlEntry = new MysqlEntry();

        List<MysqlColumn> mysqlColumns = new ArrayList<>();
        MysqlColumn mysqlColumn = new MysqlColumn();
        mysqlColumn.setName("id");
        mysqlColumn.setValue(String.valueOf(1L));
        mysqlColumns.add(mysqlColumn);

        mysqlEntry.setColumns(mysqlColumns);

        TransformResult transformResult = iEntryTransform.entry2Json(mysqlEntry, "id");
        Assert.assertEquals(transformResult.getKey(), String.valueOf(1L));

    }

    @Test
    public void testNotOk() {

        IEntryTransform iEntryTransform = EntryTransformFactory.getUpdateTransform();

        MysqlEntry mysqlEntry = new MysqlEntry();

        List<MysqlColumn> mysqlColumns = new ArrayList<>();
        MysqlColumn mysqlColumn = new MysqlColumn();
        mysqlColumn.setName("id");
        mysqlColumns.add(mysqlColumn);

        mysqlEntry.setColumns(mysqlColumns);

        TransformResult transformResult = iEntryTransform.entry2Json(mysqlEntry, "id");
        Assert.assertEquals(transformResult, null);
    }
}
