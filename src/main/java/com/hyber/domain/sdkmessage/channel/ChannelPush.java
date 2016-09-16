package com.hyber.domain.sdkmessage.channel;

import com.hyber.constants.Partners;

public class ChannelPush extends MessengerChannel {

    public ChannelPush(String text, int ttl) {
        super(text, ttl);
    }

    @Override
    public Partners getChannelName() {
        return Partners.push;
    }
}
