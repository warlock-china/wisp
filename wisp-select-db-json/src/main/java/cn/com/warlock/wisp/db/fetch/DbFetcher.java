package cn.com.warlock.wisp.db.fetch;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DbFetcher {

    List<Map<String, Object>> executeSql(String sql) throws SQLException;
}
