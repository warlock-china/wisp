package cn.com.warlock.wisp.db.config;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

@Data
public class DbConfiguration {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ConfigParser.class);

    public static Map<String, TableConfig> parse(URL xmlPath) throws Exception {
        return parse(xmlPath.openStream());
    }

    public static Map<String, TableConfig> parse(InputStream inputStream) throws Exception {

        Map<String, TableConfig> tableConfigMap = new ConfigParser().parse(inputStream);

        LOGGER.debug(tableConfigMap.toString());

        return tableConfigMap;
    }

}
