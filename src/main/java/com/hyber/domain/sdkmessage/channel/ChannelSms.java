package com.hyber.domain.sdkmessage.channel;

import com.hyber.constants.Partners;

public class ChannelSms extends Channel {
    public ChannelSms(String text, int ttl) {
        super(text, ttl);
    }

    @Override
    public Partners getChannelName() {
        return Partners.sms;
    }
}
