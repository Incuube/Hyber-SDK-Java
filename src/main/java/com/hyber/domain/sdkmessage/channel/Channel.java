package com.hyber.domain.sdkmessage.channel;

import com.hyber.constants.Partners;

public abstract class Channel {

    private String text;
    private int ttl;

    Channel(String text, int ttl) {
        this.text = text;
        this.ttl = ttl;
    }

    public String getText() {
        return text;
    }

    public int getTtl() {
        return ttl;
    }

    public abstract Partners getChannelName();

}


