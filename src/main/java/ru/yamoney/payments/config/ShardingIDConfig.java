package ru.yamoney.payments.config;

import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import io.shardingsphere.core.keygen.KeyGenerator;

public class ShardingIDConfig implements KeyGenerator {
    @Override
    public Number generateKey() {
        DefaultKeyGenerator defaultKeyGenerator = new DefaultKeyGenerator();
        return defaultKeyGenerator.generateKey();
    }
}
