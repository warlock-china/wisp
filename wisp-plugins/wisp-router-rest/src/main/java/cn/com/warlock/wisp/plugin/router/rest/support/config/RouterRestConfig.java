package cn.com.warlock.wisp.plugin.router.rest.support.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.support.config.IConfig;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import lombok.Data;

@Data
public class RouterRestConfig implements IConfig {

    protected static final Logger LOGGER = LoggerFactory.getLogger(RouterRestConfig.class);

    private int serverPort = 8888;

    private String dataSource = "";

    @Override
    public void init(IWispContext iWispContext) throws Exception {
        // port
        String port = iWispContext.getProperty("wisp.plugin.router.port", String.valueOf(serverPort));

        //
        String dataSource = iWispContext.getProperty("wisp.plugin.router.datasource");

        serverPort = Integer.parseInt(port);
        this.dataSource = dataSource;

        LOGGER.debug("router config: port:{}, dataSource:{}", port, dataSource);
    }

    @Override
    public void init() throws Exception {

    }
}
