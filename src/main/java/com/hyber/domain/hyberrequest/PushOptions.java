package com.hyber.domain.hyberrequest;

import com.hyber.domain.sdkmessage.channel.ChannelPush;
import org.json.JSONObject;

public class PushOptions {

    private String img;
    private String caption;
    private String action;

    public static PushOptions of(ChannelPush push) {
        PushOptions pushOptions = new PushOptions();
        boolean pushOptionsExists = false;
        if (push.getImageUrl() != null) {
            pushOptionsExists = true;
            pushOptions.setImg(push.getImageUrl());
        }
        if (push.getButton() != null) {
            pushOptionsExists = true;
            pushOptions.setCaption(push.getButton().getCaption());
            pushOptions.setAction(push.getButton().getActionUrl());
        }
        if (pushOptionsExists) {
            return pushOptions;
        }
        return null;
    }

    public JSONObject toJson() {
        return new JSONObject(this);
    }

    public String getImg() {
        return img;
    }

    private void setImg(String img) {
        this.img = img;
    }

    public String getCaption() {
        return caption;
    }

    private void setCaption(String caption) {
        this.caption = caption;
    }

    public String getAction() {
        return action;
    }

    private void setAction(String action) {
        this.action = action;
    }


}
