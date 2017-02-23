package cn.com.warlock.wisp.plugin.processor.kv.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.plugin.processor.kv.data.db.IDbStoreLoader;
import cn.com.warlock.wisp.plugin.processor.kv.data.db.impl.DbStoreLoaderImpl;
import cn.com.warlock.wisp.plugin.processor.kv.data.store.WispKvFactory;
import cn.com.warlock.wisp.plugin.processor.kv.data.store.IWispKv;
import cn.com.warlock.wisp.plugin.processor.kv.data.support.WispKvConfig;

/**
 * 单实例
 */
public class WispKvInstance {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WispKvInstance.class);

    // kv impl
    private static IWispKv iWispKv = null;

    // config
    private static WispKvConfig wispKvConfig = new WispKvConfig();

    //
    private static IDbStoreLoader iDbStoreLoader = new DbStoreLoaderImpl();

    private static volatile boolean isInit = false;

    public static void init(IWispContext iWispContext) {

        try {

            // init config
            wispKvConfig.init(iWispContext);

            // new wisp instance
            iWispKv = WispKvFactory.getStoreInstance(wispKvConfig.getStoreType(), iWispContext);

            // load db data and table key
            iDbStoreLoader.init(wispKvConfig);
            if (wispKvConfig.isInitWithDb()) {
                iDbStoreLoader.loadInitData(iWispKv);
            }

            isInit = true;

        } catch (Exception e) {

            LOGGER.error(e.toString());
        }
    }

    /**
     * @param key
     *
     * @return
     */
    public static String get(String tableId, String key) {

        if (isInit) {

            if (tableId == null || key == null) {
                return null;
            }

            try {

                String value = iWispKv.get(tableId, key);
                if (value == null && wispKvConfig.isGetDbWhenNotHit()) {

                    // select from db
                    return iDbStoreLoader.executeRowSql(tableId, key);
                }

                return value;

            } catch (WispProcessorException e) {

                LOGGER.error(e.toString());
                return null;
            }
        } else {

            return null;
        }
    }

    /**
     * @param key
     *
     * @return
     */
    public static boolean put(String tableId, String key, String value) {

        if (isInit) {

            try {

                iWispKv.put(tableId, key, value);
                return true;

            } catch (WispProcessorException e) {

                LOGGER.error("cannot put {} {} {} ", tableId, key, value, e.toString());
                return false;
            }

        } else {

            return false;
        }
    }

    /**
     * @param key
     *
     * @return
     */
    public static boolean delete(String tableId, String key) {

        if (isInit) {

            try {

                iWispKv.delete(tableId, key);
                return true;

            } catch (WispProcessorException e) {

                LOGGER.error("cannot put {} {} ", tableId, key, e.toString());
                return false;
            }

        } else {

            return false;
        }
    }

    /**
     * 获取 table 要设定的 KEY
     *
     * @param tableId
     *
     * @return
     */
    public static String getTableKey(String tableId) {
        return iDbStoreLoader.getTableKey(tableId);
    }

    /**
     * 关闭
     */
    public static void shutdown() {

        iWispKv.shutdown();
    }

}
