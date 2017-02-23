package cn.com.warlock.wisp.test.junit.listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import cn.com.warlock.wisp.test.datasource.SqlConfig;
import cn.com.warlock.wisp.test.datasource.UnitTestDataSource;

public class UnitTestTransactionalTestExecutionListener extends TransactionalTestExecutionListener {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UnitTestTransactionalTestExecutionListener.class);

    @Override
    public void beforeTestMethod(final TestContext testContext) throws Exception {
        this.initDatabase(testContext);
        super.beforeTestMethod(testContext);
        this.initTestData(testContext);
    }

    /**
     * 初始化数据源
     *
     * @param testContext
     */
    private void initDatabase(TestContext testContext) {
        SqlConfig.Database database = null;
        Class<?> testClass = testContext.getTestClass();
        SqlConfig sqlConfigInClass = testClass.getAnnotation(SqlConfig.class);
        if (sqlConfigInClass != null) {
            database = sqlConfigInClass.database();
        }
        Method method = testContext.getTestMethod();
        SqlConfig sqlConfigInMethod = method.getAnnotation(SqlConfig.class);
        if (sqlConfigInMethod != null) {
            database = sqlConfigInMethod.database();
        }
        if (database != null) {
            UnitTestDataSource.selectDataSource(database);
        }
    }

    private void initTestData(TestContext testContext) {
        List<String> sqlFiles = new ArrayList<String>();
        /**
         * 读取测试类指定的sql文件
         */
        Class<?> testClass = testContext.getTestClass();
        SqlConfig sqlConfigInClass = testClass.getAnnotation(SqlConfig.class);
        if (sqlConfigInClass != null) {
            String[] sqlFilesInClass = sqlConfigInClass.sqlFiles();
            if (ArrayUtils.isNotEmpty(sqlFilesInClass)) {
                sqlFiles.addAll(Arrays.asList(sqlFilesInClass));
            }
        }
        /**
         * 读取测试方法指定的sql文件
         */
        Method method = testContext.getTestMethod();
        SqlConfig sqlConfigInMethod = method.getAnnotation(SqlConfig.class);
        if (sqlConfigInMethod != null) {
            String[] sqlFilesInMethod = sqlConfigInMethod.sqlFiles();
            if (ArrayUtils.isNotEmpty(sqlFilesInMethod)) {
                sqlFiles.addAll(Arrays.asList(sqlFilesInMethod));
            }
        }
        /**
         * 执行sql
         */
        for (String sqlFile : sqlFiles) {
            LOGGER.info(String.format("execute sql file [%s]", sqlFile));
            this.executeSqlScript(testContext, sqlFile, false);
        }
    }

    /**
     * 执行sql文件
     *
     * @param testContext
     * @param sqlResourcePath
     * @param continueOnError
     *
     * @throws DataAccessException
     */
    private void executeSqlScript(TestContext testContext, String sqlResourcePath, boolean continueOnError)
            throws DataAccessException {
        Resource resource = testContext.getApplicationContext().getResource(sqlResourcePath);
        DataSource dataSource = testContext.getApplicationContext().getBean(UnitTestDataSource.class);
        new ResourceDatabasePopulator(continueOnError, false, "UTF-8", resource).execute(dataSource);
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        super.afterTestMethod(testContext);
        //清除当前选择的数据源
        UnitTestDataSource.selectDataSource(null);
    }
}
