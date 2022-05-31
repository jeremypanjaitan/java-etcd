package org.dynamicconfig;

import io.etcd.jetcd.Client;

public interface DynamicConfig {
    String getSingleConfig(String configName)
            throws java.lang.InterruptedException, java.util.concurrent.ExecutionException;

    Client getClient();

    static DynamicConfigBuilder builder() {
        return new DynamicConfigBuilder();
    }
}
