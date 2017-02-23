package cn.com.warlock.wisp.plugin.processor.kv.data.store;

import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.plugin.processor.kv.data.store.impl.WispKvStoreCodisKvImpl;
import cn.com.warlock.wisp.plugin.processor.kv.data.store.impl.WispKvStoreMemImpl;

public class WispKvFactory {

    /**
     * @param storeType
     * @param iWispContext
     *
     * @return
     *
     * @throws WispProcessorException
     */
    public static IWispKv getStoreInstance(StoreType storeType, IWispContext iWispContext) throws
            WispProcessorException {

        if (storeType.equals(StoreType.KV)) {

            return WispKvStoreMemImpl.create().build();

        } else if (storeType.equals(StoreType.CODIS)) {

            return WispKvStoreCodisKvImpl.getInstance(iWispContext);
        }

        throw new WispProcessorException("cannot find proper store type " + storeType);
    }

}
