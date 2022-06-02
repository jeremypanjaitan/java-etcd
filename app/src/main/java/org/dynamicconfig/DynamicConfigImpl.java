package org.dynamicconfig;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.KeyValue;

public class DynamicConfigImpl implements DynamicConfig {
    private Client client;
    private final String SERVICE_CONFIG = "config/%s/used/%s";
    private String serviceName;

    public DynamicConfigImpl(DynamicConfigBuilder dynamicConfigBuilder) {
        Client client = Client.builder()
                .endpoints(dynamicConfigBuilder.getEndpoints())
                .password(dynamicConfigBuilder.getPassword())
                .user(dynamicConfigBuilder.getUsername()).build();

        this.serviceName = new String(dynamicConfigBuilder.getUsername().getBytes());
        this.client = client;
    }

    @Override
    public String getSingleConfig(String configName)
            throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, JsonProcessingException {

        ByteSequence usedKey = ByteSequence
                .from(String.format(SERVICE_CONFIG, this.serviceName, configName).getBytes());
        String usedConfig = getSingleValue(usedKey);

        ByteSequence actualKey = ByteSequence
                .from(usedConfig.getBytes());
        String configValue = getSingleValue(actualKey);
        DCLog.logInfoConfig(this.getClass(),
                Config.createConfig(configName, configValue, getConfigVersion(configName)));
        return configValue;
    }

    @Override
    public List<Config> getMultipleConfig(long amount)
            throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, JsonProcessingException {

        ByteSequence usedKey = ByteSequence
                .from(String.format(SERVICE_CONFIG, this.serviceName, "").getBytes());

        List<KeyValue> values = getMultipleValue(usedKey, amount);

        ArrayList<Config> configs = new ArrayList<>();

        for (KeyValue value : values) {
            String actualKey = new String(value.getValue().getBytes());

            String configName = getConfigName(actualKey);
            String configValue = getSingleValue(ByteSequence.from(actualKey.getBytes()));

            Config config = Config.createConfig(configName, configValue, getConfigVersion(actualKey));
            DCLog.logInfoConfig(this.getClass(), config);
            configs.add(config);
        }
        return configs;
    }

    @Override
    public Client getClient() {
        return this.client;
    }

    private String getSingleValue(ByteSequence key)
            throws java.lang.InterruptedException, java.util.concurrent.ExecutionException {

        KV kvClient = client.getKVClient();
        GetOption getOption = GetOption.newBuilder().isPrefix(true).build();
        GetResponse response = kvClient.get(key, getOption).get();
        List<KeyValue> keyValues = response.getKvs();

        kvClient.close();
        return new String(keyValues.get(0).getValue().getBytes());
    }

    private List<KeyValue> getMultipleValue(ByteSequence key, long limit)
            throws java.lang.InterruptedException, java.util.concurrent.ExecutionException {

        KV kvClient = client.getKVClient();
        GetOption getOption = GetOption.newBuilder().isPrefix(true).withLimit(limit).build();
        GetResponse response = kvClient.get(key, getOption).get();

        kvClient.close();
        return response.getKvs();
    }

    private String getConfigVersion(String configName) {
        return configName.split("/")[configName.split("/").length - 1];
    }

    private String getConfigName(String configName) {
        return configName.split("/")[3];
    }

}
