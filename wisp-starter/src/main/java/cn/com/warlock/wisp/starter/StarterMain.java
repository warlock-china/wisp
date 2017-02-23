package cn.com.warlock.wisp.starter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.support.context.WispContextFactory;
import cn.com.warlock.wisp.starter.core.WispControllerFactory;
import cn.com.warlock.wisp.starter.core.IWispController;
import cn.com.warlock.wisp.core.support.context.IWispContext;

public class StarterMain {

    protected static final Logger LOGGER = LoggerFactory.getLogger(StarterMain.class);

    public static void main(String[] args) {

        // load profile
        IWispContext wispProfile = WispContextFactory.getDefaultWispContext();
        try {
            wispProfile.load();
        } catch (IOException e) {
            LOGGER.error(
                    "## something goes wrong when load profile:", e);
            System.exit(-1);
        }

        // run
        final IWispController wispController = WispControllerFactory.getDefaultController();
        wispController.run(wispProfile);

        // shutdown
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    LOGGER.info("## stop Wisp");
                    wispController.shutdown();
                } catch (Throwable e) {
                    LOGGER.warn(
                            "## something goes wrong when stopping the service:", e);
                } finally {

                    LOGGER.info("## Wisp is exit.");
                }
            }
        });
    }
}
