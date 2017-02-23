package cn.com.warlock.wisp.db.test.controller.sql;

import org.junit.Assert;
import org.junit.Test;

import cn.com.warlock.wisp.db.controller.sql.ISqlGenMgr;
import cn.com.warlock.wisp.db.controller.sql.impl.SqlGenMgr;

public class SqlGenMgrTestCase {

    @Test
    public void test() {

        ISqlGenMgr sqlGenMgr = new SqlGenMgr();

        String sql = sqlGenMgr.genSql("test.user", "id", String.valueOf(1L));

        System.out.println(sql);

        Assert.assertEquals(sql, "select * from test.user where id=1");

    }
}
