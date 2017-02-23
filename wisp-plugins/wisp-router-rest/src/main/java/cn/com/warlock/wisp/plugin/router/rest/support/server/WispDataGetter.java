package cn.com.warlock.wisp.plugin.router.rest.support.server;

import cn.com.warlock.wisp.core.plugin.router.IWispDataRouter;
import lombok.Data;

@Data
public class WispDataGetter {

    private static IWispDataRouter iWispDataRouter = null;

    public static String get(String tableId, String key) {
        if (iWispDataRouter != null) {

            if (tableId == null || key == null) {
                return "";
            }

            return iWispDataRouter.get(tableId, key);
        }

        return "";
    }

    public static void setupIWispDataRouter(IWispDataRouter dataRouter) {
        iWispDataRouter = dataRouter;
    }
}
