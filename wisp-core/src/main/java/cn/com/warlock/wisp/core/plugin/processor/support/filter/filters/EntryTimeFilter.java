package cn.com.warlock.wisp.core.plugin.processor.support.filter.filters;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.dto.MysqlEntryWrap;
import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.IEntryFilter;
import cn.com.warlock.wisp.core.plugin.processor.support.filter.IEntryFilterChain;

public class EntryTimeFilter implements IEntryFilter {

    protected static final Logger LOGGER = LoggerFactory.getLogger(EntryTimeFilter.class);

    /**
     * 过滤掉LOG日志比当前时间小较多的数据, 避免长时间处理旧数据
     *
     * @param entry
     * @param iEntryFilterChain
     *
     * @throws WispProcessorException
     */
    @Override
    public void doFilter(MysqlEntryWrap entry, IEntryFilterChain iEntryFilterChain) throws WispProcessorException {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long currentTime = timestamp.getTime();

        long logTime = entry.getMysqlEntry().getTime();

        // 5s
        if (logTime + 1000 * 5 < currentTime) {

            LOGGER.warn("ignore entry because of smaller time logTime:{}, currentTime:{}, entry{} ", logTime,
                    currentTime, entry);

        } else {
            iEntryFilterChain.doFilter(entry);
        }
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }
}
