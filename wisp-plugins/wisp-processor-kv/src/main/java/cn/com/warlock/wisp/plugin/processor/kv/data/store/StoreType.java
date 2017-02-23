package cn.com.warlock.wisp.plugin.processor.kv.data.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum StoreType {

    KV("kv"), CODIS("codis"), UNKNOWN("undown");

    protected static final Logger LOGGER = LoggerFactory.getLogger(StoreType.class);
    private String name;

    StoreType(String name) {
        this.name = name;
    }

    public static StoreType get(String name) {
        if (null == name) {
            return null;
        }
        for (StoreType temp : StoreType.values()) {
            if (temp.getName().equals(name)) {
                return temp;
            }
        }

        LOGGER.error("cannot recognize store type: {}", name);
        return StoreType.UNKNOWN;
    }

    public String getName() {
        return name;
    }
}
