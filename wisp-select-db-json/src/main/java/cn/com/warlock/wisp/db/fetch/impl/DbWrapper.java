package cn.com.warlock.wisp.db.fetch.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPoolFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库封装
 */
public class DbWrapper {

    private static final Logger logger = LoggerFactory.getLogger(DbWrapper.class);

    private DataSource dataSource = null;

    public void setup(String driverUrl, String connectURI, String userName, String password)
            throws ClassNotFoundException {
        dataSource = setupDataSource(driverUrl, connectURI, userName, password);
    }

    public void setup(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {

        // dataSource.getConnection().
        return dataSource.getConnection();
    }

    public void cleanUp(Connection conn, Statement stmt, ResultSet rs) {

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private DataSource setupDataSource(String driver, String connectURI,
                                       String userName, String passwd) throws ClassNotFoundException {

        // driver
        Class.forName(driver);

        //
        // First, we'll need a ObjectPool that serves as the
        // actual pool of connections.
        //
        // We'll use a GenericObjectPool instance, although
        // any ObjectPool implementation will suffice.
        //
        GenericObjectPool connectionPool = new GenericObjectPool(null);
        // 设置在getConnection时验证Connection是否有效
        connectionPool.setTestOnBorrow(true);

        //
        // Next, we'll create a ConnectionFactory that the
        // pool will use to create Connections.
        // We'll use the DriverManagerConnectionFactory,
        // using the connect string passed in the command line
        // arguments.
        //
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
                connectURI, userName, passwd);

        // null can be used as parameter because this parameter is set in
        // PoolableConnectionFactory when creating a new PoolableConnection
        KeyedObjectPoolFactory statementPool = new GenericKeyedObjectPoolFactory(
                null);

        //
        // Now we'll create the PoolableConnectionFactory, which wraps
        // the "real" Connections created by the ConnectionFactory with
        // the classes that implement the pooling functionality.
        //
        new PoolableConnectionFactory(connectionFactory, connectionPool,
                statementPool, "select now()", false, true);

        //
        // Finally, we create the PoolingDriver itself,
        // passing in the object pool we created.
        //
        PoolingDataSource dataSource = new PoolingDataSource(connectionPool);

        return dataSource;
    }
}
