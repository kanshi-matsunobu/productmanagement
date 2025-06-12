package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.api")
public class ApiKeyProperties {
    private String key;
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
}