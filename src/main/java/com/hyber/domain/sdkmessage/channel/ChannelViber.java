package com.hyber.domain.sdkmessage.channel;

import com.hyber.constants.Partners;
import org.json.JSONObject;

import static com.hyber.constants.jsonfields.RequestFields.*;
import static com.hyber.constants.jsonfields.RequestFields.IMG;

public class ChannelViber extends MessengerChannel {

    private String iosExpirityText;

    public ChannelViber(int ttl) {
        super(ttl);
    }

    @Override
    public Partners getChannel() {
        return Partners.viber;
    }


    public String getIosExpirityText() {
        return iosExpirityText;
    }

    public void setIosExpirityText(String iosExpirityText) {
        this.iosExpirityText = iosExpirityText;
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
        if (iosExpirityText != null){
            jsonObject.put(IOS_EXPIRITY_TEXT, iosExpirityText);
        }
        return jsonObject;
    }
}
