package cn.com.warlock.wisp.plugin.processor.kv.data.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.support.config.IConfig;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.plugin.processor.kv.data.store.StoreType;
import lombok.Data;

@Data
public class WispKvConfig implements IConfig {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WispKvConfig.class);

    private final static String CONFIG_FILE_NAME = "wisp-db-kv.xml";

    private StoreType storeType;

    private String dbLoaderFilePath;

    private boolean isInitWithDb = true;

    private boolean getDbWhenNotHit = false;

    @Override
    public void init(IWispContext iWispContext) throws Exception {

        // 默认是 CONFIG_FILE_NAME
        this.dbLoaderFilePath =
                iWispContext.getProperty("wisp.plugin.processor.db.loader.filepath", CONFIG_FILE_NAME);

        // 默认是KV
        String storeTypeStr = iWispContext.getProperty("wisp.plugin.processor.kv.type", StoreType.KV.getName());

        // 默认是true
        isInitWithDb = Boolean.parseBoolean(iWispContext.getProperty("wisp.plugin.processor.db.loader.init",
                "true"));

        // 默认是false
        getDbWhenNotHit =
                Boolean.parseBoolean(iWispContext.getProperty("wisp.plugin.processor.db.get.row.when.not.hit",
                        "false"));

        this.storeType = StoreType.get(storeTypeStr);
    }

    @Override
    public void init() throws Exception {
    }

}
