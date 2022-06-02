package org.dynamicconfig;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.etcd.jetcd.Client;

public interface DynamicConfig {
    String getSingleConfig(String configName)
            throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, JsonProcessingException;

    List<Config> getMultipleConfig(long amount)
            throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, JsonProcessingException;

    Client getClient();

    static DynamicConfigBuilder builder() {
        return new DynamicConfigBuilder();
    }
}
