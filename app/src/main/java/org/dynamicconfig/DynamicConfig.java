package org.dynamicconfig;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.etcd.jetcd.Client;

public interface DynamicConfig {
    String getSingleConfig(String configName)
            throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, JsonProcessingException;

    Client getClient();

    static DynamicConfigBuilder builder() {
        return new DynamicConfigBuilder();
    }
}
