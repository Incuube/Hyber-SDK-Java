package com.hyber.domain.sdkmessage.channel;

import com.hyber.constants.Partners;
import org.json.JSONObject;

import static com.hyber.constants.jsonfields.RequestFields.*;


public class ChannelPush extends MessengerChannel {

    private String title;

    public ChannelPush(int ttl) {
        super(ttl);
    }

    @Override
    public Partners getChannel() {
        return Partners.push;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TTL, getTtl());
        if (getText() != null){
            jsonObject.put(TEXT, getText());
        }
        if (getButton() != null){
            jsonObject.put(ACTION, getButton().getActionUrl());
            jsonObject.put(CAPTION, getButton().getCaption());
        }
        if (getImageUrl() != null){
            jsonObject.put(IMG, getImageUrl());
        }
        if (title != null){
            jsonObject.put(PUSH_TITLE, title);
        }
        return jsonObject;
    }
}
