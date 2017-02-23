package cn.com.warlock.wisp.db.fetch;

import javax.sql.DataSource;

public interface DbDataContextAware {

    void setDbDataContext(DbDataContext dbDataContext) throws ClassNotFoundException;

    void setDataSource(DataSource dataSource);
}
