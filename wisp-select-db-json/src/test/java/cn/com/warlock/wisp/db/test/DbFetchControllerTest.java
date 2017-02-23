package cn.com.warlock.wisp.db.test;

import java.util.Map;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import cn.com.warlock.wisp.db.DbFetchControllerFactory;
import cn.com.warlock.wisp.db.IDbFetchController;
import cn.com.warlock.wisp.db.fetch.DbFetcher;
import cn.com.warlock.wisp.db.fetch.DbFetcherFactory;
import cn.com.warlock.wisp.db.fetch.impl.DbFetcherImpl;
import cn.com.warlock.wisp.test.h2.H2BaseTestCase;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class DbFetchControllerTest extends H2BaseTestCase {

    IDbFetchController dbFetchController = DbFetchControllerFactory.getDefaultDbController();

    @Test
    public void test() {

        final DataSource dataSource = this.getDataSource();

        //
        // mock up factory method
        //
        new MockUp<DbFetcherFactory>() {

            // mock up database
            @Mock
            public DbFetcher getDefaultDbFetcher(String driverClass, String dbUrl, String userName,
                                                 String userPassword)
                    throws ClassNotFoundException {

                DbFetcher dbFetcher = new DbFetcherImpl();

                // set up h2 data base
                ((DbFetcherImpl) dbFetcher).setDataSource(dataSource);

                return dbFetcher;
            }
        };

        try {

            dbFetchController.init("wisp-db-kv.xml");
            Map<String, Map<String, String>> dbKvs = dbFetchController.getInitDbKv();

            for (String tableId : dbKvs.keySet()) {

                System.out.println("table identify: " + tableId);
                System.out.println("table kv:" + dbKvs.get(tableId));
                System.out.println("table key: " + dbFetchController.getTableKey(tableId));

                String value = dbFetchController.getRowByExecuteSql(tableId, String.valueOf(1L));
                System.out.println("execute sql value: " + value);
            }

        } catch (Exception e) {

            Assert.assertTrue(false);
        }
    }
}
