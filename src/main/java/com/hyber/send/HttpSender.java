package com.hyber.send;

import com.hyber.constants.HyberConstants;

public abstract class HttpSender implements Sender {

    private String urlPattern = HyberConstants.URL_PATTERN;
    private Integer connectionTimeout = HyberConstants.DEFAULT_CONNECTION_TIMEOUT;
    private Integer readTimeout = HyberConstants.DEFAULT_READ_TIMEOUT;


    public String getUrlPattern() {
        return urlPattern;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }
}
