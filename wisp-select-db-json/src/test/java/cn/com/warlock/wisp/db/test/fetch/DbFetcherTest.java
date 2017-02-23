package cn.com.warlock.wisp.db.test.fetch;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import cn.com.warlock.wisp.db.fetch.DbFetcher;
import cn.com.warlock.wisp.db.fetch.DbFetcherFactory;
import cn.com.warlock.wisp.test.h2.H2BaseTestCase;

public class DbFetcherTest extends H2BaseTestCase {

    @Test
    public void test() {

        try {

            DbFetcher dbFetcher = DbFetcherFactory.getDefaultDbFetcher(this.getDataSource());

            List<Map<String, Object>> results = dbFetcher.executeSql("select * from test.user");
            for (Map<String, Object> map : results) {
                System.out.println(map.toString());
            }
            Assert.assertEquals(results.size(), 2);

            results = dbFetcher.executeSql("select * from test2.store");
            for (Map<String, Object> map : results) {
                System.out.println(map.toString());
            }
            Assert.assertEquals(results.size(), 5);

        } catch (Exception e) {

            e.printStackTrace();

            Assert.assertTrue(false);
        }
    }
}
