package org.dynamicconfig;

public class Config {
    private String configName;
    private String configValue;
    private String version;

    public Config(String configName, String configValue, String version) {
        this.configName = configName;
        this.configValue = configValue;
        this.version = version;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
