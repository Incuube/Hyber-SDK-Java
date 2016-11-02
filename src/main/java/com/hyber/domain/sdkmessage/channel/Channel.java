package com.hyber.domain.sdkmessage.channel;

import com.hyber.constants.Partners;
import org.json.JSONObject;

public abstract class Channel {

    private String text;
    private int ttl;

    Channel(int ttl) {
        this.ttl = ttl;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTtl() {
        return ttl;
    }

    public abstract Partners getChannel();

    public abstract JSONObject toJson();

}


