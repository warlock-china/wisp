package cn.com.warlock.wisp.db.fetch.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.db.fetch.DbDataContext;
import cn.com.warlock.wisp.db.fetch.DbDataContextAware;
import cn.com.warlock.wisp.db.fetch.DbFetcher;

public class DbFetcherImpl implements DbFetcher, DbDataContextAware {

    private static final Logger logger = LoggerFactory.getLogger(DbFetcherImpl.class);

    private DbWrapper dbWrapper;

    public List<Map<String, Object>> executeSql(String sql) {

        logger.debug(sql);

        Connection conn = null;

        List<Map<String, Object>> listOfMaps = null;

        try {
            conn = dbWrapper.getConnection();

            QueryRunner queryRunner = new QueryRunner();
            listOfMaps = queryRunner.query(conn, sql, new MapListHandler());

            logger.debug(listOfMaps.toString());

            return listOfMaps;

        } catch (SQLException e) {

            logger.error(e.getMessage() + " sql: " + sql, e);

        } catch (SecurityException e) {

            logger.error(e.getMessage() + " sql: " + sql, e);

        } finally {
            dbWrapper.cleanUp(conn, null, null);
        }

        return new ArrayList<Map<String, Object>>();
    }

    public void setDbDataContext(DbDataContext dbDataContext) throws ClassNotFoundException {

        dbWrapper = new DbWrapper();
        dbWrapper.setup(dbDataContext.getDriverClass(), dbDataContext.getDbUrl(), dbDataContext.getUserName(),
                dbDataContext.getUserPassword());
    }

    @Override
    public void setDataSource(DataSource dataSource) {

        dbWrapper = new DbWrapper();
        dbWrapper.setup(dataSource);
    }
}
