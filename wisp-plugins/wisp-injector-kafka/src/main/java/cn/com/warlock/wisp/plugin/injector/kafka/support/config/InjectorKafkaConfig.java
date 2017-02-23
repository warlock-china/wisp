package cn.com.warlock.wisp.plugin.injector.kafka.support.config;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import cn.com.warlock.wisp.core.support.config.IConfig;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;
import lombok.Data;
import scala.actors.threadpool.Arrays;

@Data
public class InjectorKafkaConfig implements IConfig {

    protected static final Logger LOGGER = LoggerFactory.getLogger(InjectorKafkaConfig.class);

    private String[] topics;
    private List<String> topicList;

    // kafka consumer
    private ConsumerConnector consumer = null;

    //
    private final static String CONFIG_FILE_NAME = "kafka.properties";

    @Override
    public void init(IWispContext iWispContext) throws Exception {

        Preconditions.checkNotNull(iWispContext, "iWispContext can not be null");

        //
        String currentTopics = iWispContext.getProperty("wisp.plugin.injector.kafka.topics");
        this.topics = currentTopics.split(",");

        topicList = Arrays.asList(topics);

        LOGGER.debug("injector config: topics:{}", topicList);

        // load kafka config
        loadConfigAndInit();
    }

    @Override
    public void init() throws Exception {
    }

    private void loadConfigAndInit() throws Exception {

        Properties kafkaProps = new Properties();
        InputStream inputStream = InjectorKafkaConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME);

        if (inputStream != null) {

            kafkaProps.load(inputStream);

            LOGGER.info("loading injector kafka config file {}", CONFIG_FILE_NAME);

            ConsumerConfig consumerConfig = new ConsumerConfig(kafkaProps);

            // consumer
            this.consumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);

        } else {

            LOGGER.warn("cannot find config file {}", CONFIG_FILE_NAME);
        }

    }
}
