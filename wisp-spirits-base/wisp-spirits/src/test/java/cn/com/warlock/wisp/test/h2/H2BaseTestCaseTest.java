package cn.com.warlock.wisp.test.h2;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import cn.com.warlock.wisp.test.h2.H2BaseTestCase;

public class H2BaseTestCaseTest extends H2BaseTestCase {

    @Test
    public void foo() {

        try {

            String query = "select * from test.t_demo";
            List<Map<String, Object>> listOfMaps = executeSql(query);

            System.out.println(new Gson().toJson(listOfMaps));

            Assert.assertEquals(listOfMaps.size(), 2);

        } catch (SQLException se) {

            Assert.assertTrue(false);
            throw new RuntimeException("Couldn't query the database.", se);

        }

    }
}
