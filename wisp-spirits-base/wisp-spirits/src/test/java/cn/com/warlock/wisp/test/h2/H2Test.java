package cn.com.warlock.wisp.test.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.zapodot.junit.db.EmbeddedDatabaseRule;

import com.google.gson.Gson;

public class H2Test {

    @Rule
    public EmbeddedDatabaseRule embeddedDatabaseRule = EmbeddedDatabaseRule.builder()
            .withInitialSqlFromResource(
                    "classpath:sql/schema/demo_schema.sql")
            .build();

    @Test
    public void testUsingConnectionUrl() throws Exception {

        try (final Connection connection = DriverManager.getConnection(embeddedDatabaseRule.getConnectionJdbcUrl())) {

            QueryRunner queryRunner = new QueryRunner();

            List<Map<String, Object>> listOfMaps = null;
            listOfMaps = queryRunner.query(connection, "select * from test.t_demo", new MapListHandler());
            System.out.println(new Gson().toJson(listOfMaps));

            Assert.assertEquals(0, listOfMaps.size());
        }

    }
}
