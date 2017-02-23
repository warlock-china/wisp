package cn.com.warlock.wisp.db.fetch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DbDataContext {

    private String driverClass;
    private String dbUrl;
    private String userName;
    private String userPassword;
}
