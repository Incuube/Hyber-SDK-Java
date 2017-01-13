package com.hyber.sdk.domain;

import com.hyber.sdk.constants.jsonfields.RequestFields;
import org.json.JSONObject;

public final class ViberOptions {

    private String text;
    private Integer ttl;
    private String img;
    private String caption;
    private String action;
    private String iosExpirityText;

    ViberOptions(String text, Integer ttl, String img, String caption, String action, String iosExpirityText) {
        this.text = text;
        this.ttl = ttl;
        this.img = img;
        this.caption = caption;
        this.action = action;
        this.iosExpirityText = iosExpirityText;
    }

    JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(RequestFields.TTL, ttl);
        jsonObject.put(RequestFields.TEXT, text);
        jsonObject.put(RequestFields.ACTION, action);
        jsonObject.put(RequestFields.CAPTION, caption);
        jsonObject.put(RequestFields.IMG, img);
        jsonObject.put(RequestFields.IOS_EXPIRITY_TEXT, iosExpirityText);
        return jsonObject;
    }

    static ViberOptionsBuilder builder(Message.MessageBuilder builder, ViberOptions viber) {
        return new ViberOptionsBuilder(builder, viber);
    }

    public static class ViberOptionsBuilder {
        private String text;
        private Integer ttl;
        private String img;
        private String caption;
        private String action;
        private String iosExpirityText;
        private Message.MessageBuilder v2RequestBuilder;

        ViberOptionsBuilder(Message.MessageBuilder messageBuilder, ViberOptions viber) {
            this.v2RequestBuilder = messageBuilder;
            if (viber != null){
                text= viber.getText();
                ttl = viber.getTtl();
                img = viber.getImg();
                caption = viber.getCaption();
                action = viber.getAction();
                iosExpirityText = viber.getIosExpirityText();
            }
        }

        public ViberOptionsBuilder text(String text) {
            this.text = text;
            return this;
        }

        public ViberOptionsBuilder ttl(Integer ttl) {
            this.ttl = ttl;
            return this;
        }

        public ViberOptionsBuilder img(String img) {
            this.img = img;
            return this;
        }

        public ViberOptionsBuilder caption(String caption) {
            this.caption = caption;
            return this;
        }

        public ViberOptionsBuilder action(String action) {
            this.action = action;
            return this;
        }

        public ViberOptionsBuilder iosExpirityText(String iosExpirityText) {
            this.iosExpirityText = iosExpirityText;
            return this;
        }

        public Message.MessageBuilder end() {
            return v2RequestBuilder.viber(build());
        }

        private ViberOptions build() {
            return new ViberOptions(text, ttl, img, caption, action, iosExpirityText);
        }

    }

    public String getText() {
        return text;
    }

    public Integer getTtl() {
        return ttl;
    }

    public String getImg() {
        return img;
    }

    public String getCaption() {
        return caption;
    }

    public String getAction() {
        return action;
    }

    public String getIosExpirityText() {
        return iosExpirityText;
    }
}
