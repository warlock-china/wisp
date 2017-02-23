package cn.com.warlock.wisp.plugin.processor.kv.data.store.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.exception.WispProcessorException;
import cn.com.warlock.wisp.core.support.context.IWispContext;
import cn.com.warlock.wisp.plugin.processor.kv.data.store.IWispKv;
import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

public class WispKvStoreCodisKvImpl implements IWispKv {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WispKvStoreCodisKvImpl.class);

    private String redisPrefix = "";

    //
    private static WispKvStoreCodisConfig processorCodisConfig = new WispKvStoreCodisConfig();

    private JedisResourcePool jedisPool;

    private WispKvStoreCodisKvImpl(JedisPoolConfig poolConfig, String zkAddr, String zkProxyDir, int zkTimeout,
                                     String redisPrefix) {
        this.redisPrefix = redisPrefix;
        jedisPool = RoundRobinJedisPool.create()
                .curatorClient(zkAddr, zkTimeout).zkProxyDir(zkProxyDir).poolConfig(poolConfig).build();
    }

    public static Builder create() {
        return new Builder();
    }

    /**
     * Builder
     */
    public static final class Builder {

        private String zkProxyDir;
        private String zkAddr;
        private int zkTimeout;
        private String redisPrefix = "__wisp__";
        ;
        private JedisPoolConfig jedisPoolConfig;

        private Builder() {
        }

        /**
         * Set jedis pool config.
         */
        public Builder poolConfig(JedisPoolConfig poolConfig) {
            this.jedisPoolConfig = poolConfig;
            return this;
        }

        public Builder zkSet(String zkAddr, String zkProxyDir, int zkTimeout) {
            this.zkAddr = zkAddr;
            this.zkProxyDir = zkProxyDir;
            this.zkTimeout = zkTimeout;
            return this;
        }

        public Builder redisPrefix(String redisPrefix) {
            this.redisPrefix = redisPrefix;
            return this;
        }

        private void validate() {
        }

        /**
         */
        public IWispKv build() {
            validate();
            return new WispKvStoreCodisKvImpl(jedisPoolConfig, zkAddr, zkProxyDir, zkTimeout, redisPrefix);
        }
    }

    /**
     * @param tableId
     * @param key
     *
     * @return
     *
     * @throws WispProcessorException
     */
    @Override
    public String get(String tableId, String key) throws WispProcessorException {

        Jedis jedis = null;
        try {

            LOGGER.debug("jedis get: {} {}", tableId, key);

            jedis = jedisPool.getResource();

            String hKey = redisPrefix + tableId;
            return jedis.hget(hKey, key);

        } catch (Exception e) {

            throw new WispProcessorException(e);
        } finally {

            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * @param tableId
     * @param key
     * @param value
     *
     * @throws WispProcessorException
     */
    @Override
    public void put(String tableId, String key, String value) throws WispProcessorException {

        Jedis jedis = null;
        try {

            jedis = jedisPool.getResource();

            String hKey = redisPrefix + tableId;
            jedis.hset(hKey, key, value);

        } catch (Exception e) {

            throw new WispProcessorException(e);
        } finally {

            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public void delete(String tableId, String key) throws WispProcessorException {
        Jedis jedis = null;
        try {

            LOGGER.debug("jedis get: {} {}", tableId, key);

            jedis = jedisPool.getResource();

            String hKey = redisPrefix + tableId;
            jedis.hdel(hKey, key);

        } catch (Exception e) {

            throw new WispProcessorException(e);
        } finally {

            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void shutdown() {

        if (jedisPool != null) {
            try {
                jedisPool.close();
            } catch (IOException e) {

                LOGGER.error(e.toString());
            }
        }
    }

    public static IWispKv getInstance(IWispContext iWispContext) throws WispProcessorException {

        try {

            processorCodisConfig.init(iWispContext);
            return WispKvStoreCodisKvImpl.create().poolConfig(processorCodisConfig.getJedisPoolConfig())
                    .zkSet(processorCodisConfig.getZkServer(), processorCodisConfig.getZkPath(), processorCodisConfig
                            .getZkTimeout()).build();

        } catch (Exception e) {
            throw new WispProcessorException(e);
        }

    }
}
