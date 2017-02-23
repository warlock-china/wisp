package cn.com.warlock.wisp.db;

import cn.com.warlock.wisp.db.controller.DbFetchControllerImpl;

public class DbFetchControllerFactory {

    public static IDbFetchController getDefaultDbController() {

        IDbFetchController dbFetchController = new DbFetchControllerImpl();
        return dbFetchController;
    }
}
