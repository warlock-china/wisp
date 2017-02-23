package cn.com.warlock.wisp.test.h2;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.zapodot.junit.db.EmbeddedDatabaseRule;

import cn.com.warlock.wisp.test.h2.config.ConfigParser;
import cn.com.warlock.wisp.test.h2.config.InitDbConfig;

public abstract class H2BaseTestCase {

    protected Connection connection;
    protected DataSource dataSource;

    protected static String db2XmlFile = "jutf-h2.xml";

    protected static InitDbConfig initDbConfig;

    static {

        URL url = H2BaseTestCase.class.getClassLoader().getResource(db2XmlFile);

        try {
            initDbConfig = ConfigParser.parse(url.openStream());

        } catch (Exception e) {
            System.err.println(e.toString());
            Assert.assertTrue(false);
        }
    }

    @Rule
    public EmbeddedDatabaseRule dbRule = EmbeddedDatabaseRule
            .builder()
            .withMode("MYSQL")
            .withInitialSql(initDbConfig.getTotalSchema() + initDbConfig.getTotalData())
            .build();

    @Before
    public void setup() {
        try {

            connection = DriverManager.getConnection(dbRule.getConnectionJdbcUrl());
            dataSource = dbRule.getDataSource();

        } catch (Exception e) {
            System.err.println(e.toString());
            Assert.assertTrue(false);
        }
    }

    public List<Map<String, Object>> executeSql(String sql) throws SQLException {

        try {

            QueryRunner queryRunner = new QueryRunner();

            return queryRunner.query(connection, sql, new MapListHandler());

        } finally {

            DbUtils.closeQuietly(connection);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Connection getConnection() {
        return connection;
    }
}
