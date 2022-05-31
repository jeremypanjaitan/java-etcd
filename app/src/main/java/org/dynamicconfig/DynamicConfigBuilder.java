package org.dynamicconfig;

import io.etcd.jetcd.ByteSequence;

public class DynamicConfigBuilder {
    private ByteSequence username;
    private ByteSequence password;
    private String[] endpoints;

    public DynamicConfigBuilder endpoints(String... endpoints) {
        this.endpoints = endpoints;
        return this;
    }

    public DynamicConfigBuilder setAuth(String username, String password) {
        ByteSequence user = ByteSequence.from(username.getBytes());
        ByteSequence pass = ByteSequence.from(password.getBytes());
        this.username = user;
        this.password = pass;
        return this;
    }

    public ByteSequence getUsername() {
        return this.username;
    }

    public ByteSequence getPassword() {
        return this.password;
    }

    public String[] getEndpoints() {
        return this.endpoints;
    }

    public DynamicConfig build() {
        return new DynamicConfigImpl(this);

    }
}
