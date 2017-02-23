package cn.com.warlock.wisp.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Mysql entry
 *
 */
@Data
@AllArgsConstructor
public class MysqlEntryWrap {

    /**
     * topic source
     */
    private String topic;

    private MysqlEntry mysqlEntry;

}
