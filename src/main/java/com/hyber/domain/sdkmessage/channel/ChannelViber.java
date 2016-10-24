package com.hyber.domain.sdkmessage.channel;

import com.hyber.constants.Partners;

public class ChannelViber extends MessengerChannel {

    private String iosExpirityText;

    public ChannelViber(String text, int ttl) {
        super(text, ttl);
    }

    @Override
    public Partners getChannelName() {
        return Partners.viber;
    }


    public String getIosExpirityText() {
        return iosExpirityText;
    }

    public void setIosExpirityText(String iosExpirityText) {
        this.iosExpirityText = iosExpirityText;
    }

}
