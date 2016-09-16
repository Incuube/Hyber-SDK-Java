package com.hyber.domain.hyberrequest;

import com.hyber.domain.sdkmessage.channel.ChannelViber;
import org.json.JSONObject;

import static com.hyber.constants.jsonfields.RequestFields.*;

public final class ViberOptions {

    private String img;
    private String caption;
    private String action;
    private String iosExpirityText;


    private ViberOptions() {
    }

    public static ViberOptions of(ChannelViber viber) {
        ViberOptions viberOptions = new ViberOptions();
        boolean viberOptionsExists = false;
        if (viber.getImageUrl() != null) {
            viberOptionsExists = true;
            viberOptions.setImg(viber.getImageUrl());
        }
        if (viber.getIosExpirityText() != null) {
            viberOptionsExists = true;
            viberOptions.setIosExpirityText(viber.getIosExpirityText());
        }
        if (viber.getButton() != null) {
            viberOptionsExists = true;
            viberOptions.setCaption(viber.getButton().getCaption());
            viberOptions.setAction(viber.getButton().getActionUrl());
        }
        if (viberOptionsExists) {
            return viberOptions;
        }
        return null;
    }

    public JSONObject toJson() {
        JSONObject viberOptionsJson = new JSONObject();
        if (iosExpirityText != null) {
            viberOptionsJson.put(IOS_EXPIRITY_TEXT, iosExpirityText);
        }
        if (caption != null) {
            viberOptionsJson.put(CAPTION, caption);
        }
        if (action != null) {
            viberOptionsJson.put(ACTION, action);
        }
        if (img != null) {
            viberOptionsJson.put(IMG, img);
        }
        return viberOptionsJson;
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

    public String getIosExpirityText() {
        return iosExpirityText;
    }

    private void setIosExpirityText(String iosExpirityText) {
        this.iosExpirityText = iosExpirityText;
    }

}
