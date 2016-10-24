package com.hyber.domain.hyberrequest;

public class JsonChannel {

    private String channel;
    private int ttl;

    public JsonChannel(String channel, int ttl) {
        this.channel = channel;
        this.ttl = ttl;
    }

    public String getChannel() {
        return channel;
    }

    public int getTtl() {
        return ttl;
    }

}
