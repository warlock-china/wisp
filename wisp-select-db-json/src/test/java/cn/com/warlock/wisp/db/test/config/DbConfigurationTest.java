package cn.com.warlock.wisp.db.test.config;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import cn.com.warlock.wisp.db.config.DbConfiguration;

public class DbConfigurationTest {

    @Test
    public void test() {

        URL url = DbConfigurationTest.class.getClassLoader().getResource("wisp-db-kv.xml");

        try {

            DbConfiguration.parse(url);

        } catch (Exception e) {
            e.printStackTrace();

            Assert.assertTrue(false);
        }
    }
}
