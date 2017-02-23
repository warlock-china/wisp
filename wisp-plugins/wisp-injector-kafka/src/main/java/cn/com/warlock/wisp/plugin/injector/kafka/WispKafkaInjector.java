package cn.com.warlock.wisp.plugin.injector.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.exception.WispInjectorException;
import cn.com.warlock.wisp.core.exception.WispInjectorInitException;
import cn.com.warlock.wisp.core.plugin.injector.IWispInjector;
import cn.com.warlock.wisp.core.plugin.injector.IInjectorEntryProcessorAware;
import cn.com.warlock.wisp.core.plugin.injector.template.InjectorEventProcessTemplate;
import cn.com.warlock.wisp.core.support.annotation.PluginName;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.core.support.context.IWispContextAware;
import cn.com.warlock.wisp.plugin.injector.kafka.support.InjectorSupport;
import cn.com.warlock.wisp.plugin.injector.kafka.support.config.InjectorKafkaConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

@PluginName(name = "wisp-injector-kafka")
public class WispKafkaInjector implements IWispInjector, IInjectorEntryProcessorAware, IWispContextAware {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WispKafkaInjector.class);

    // config
    private InjectorKafkaConfig injectorKafkaConfig = new InjectorKafkaConfig();

    // process entry template
    private InjectorEventProcessTemplate injectorEventProcessTemplate;

    // thread executors
    private ExecutorService executor = null;

    //
    private IWispContext iWispContext = null;

    @Override
    public void init() throws WispInjectorInitException {

        try {
            injectorKafkaConfig.init(iWispContext);
        } catch (Exception e) {
            throw new WispInjectorInitException(e);
        }
    }

    @Override
    public void run() throws WispInjectorException {

        if (injectorKafkaConfig.getConsumer() != null) {

            // set topic map
            Map<String, Integer> topicCountMap = new HashMap<String, Integer>();

            for (String topic : injectorKafkaConfig.getTopicList()) {
                topicCountMap.put(topic, 1);

            }

            // get stream
            Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap =
                    injectorKafkaConfig.getConsumer().createMessageStreams
                            (topicCountMap);

            // executors
            executor = Executors.newFixedThreadPool(consumerMap.size());
            for (String topicStr : consumerMap.keySet()) {

                List<KafkaStream<byte[], byte[]>> newOrderStreams = consumerMap.get(topicStr);

                ConsumerIterator<byte[], byte[]> it = newOrderStreams.get(0).iterator();

                executor.submit(new InjectorSupport(it, topicStr, injectorEventProcessTemplate));
            }
        }
    }

    @Override
    public void shutdown() throws WispInjectorException {

        if (executor != null) {
            executor.shutdown();

            try {
                if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                    LOGGER.info("Timed out waiting for consumer threads (topic: {} ) to shut down, exiting uncleanly",
                            injectorKafkaConfig.getTopicList());
                }
            } catch (InterruptedException e) {
                LOGGER.warn("Interrupted during shutdown, exiting uncleanly");
            }

            // shut down callback
            if (injectorEventProcessTemplate != null) {

                injectorEventProcessTemplate.shutdown();
            }
        }

        LOGGER.info(WispKafkaInjector.class.toString() + " stops gracefully");
    }

    @Override
    public void setupProcessEntry(InjectorEventProcessTemplate injectorEventProcessTemplate) {
        this.injectorEventProcessTemplate = injectorEventProcessTemplate;
    }

    @Override
    public void setWispContext(IWispContext iWispContext) {
        this.iWispContext = iWispContext;
    }
}
