
package cn.com.warlock.wisp.test.datasource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SqlConfig {
    // 数据库 MYSQL or H2
    public Database database();

    // sql文件路径
    public String[] sqlFiles() default {};

    /**
     * 目前支持两种数据库
     */
    public static enum Database {
        MYSQL("mysql"), H2("h2");
        private String name;

        private Database(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
