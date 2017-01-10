package com.hyber.domain;

import org.json.JSONObject;

import static com.hyber.constants.jsonfields.RequestFields.*;

final class ViberOptions {

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
        jsonObject.put(TTL, ttl);
        jsonObject.put(TEXT, text);
        jsonObject.put(ACTION, action);
        jsonObject.put(CAPTION, caption);
        jsonObject.put(IMG, img);
        jsonObject.put(IOS_EXPIRITY_TEXT, iosExpirityText);
        return jsonObject;
    }

    static ViberOptionsBuilder builder(Message.MessageBuilder builder) {
        return new ViberOptionsBuilder(builder);
    }

    public static class ViberOptionsBuilder {
        private String text;
        private Integer ttl;
        private String img;
        private String caption;
        private String action;
        private String iosExpirityText;
        private Message.MessageBuilder v2RequestBuilder;

        ViberOptionsBuilder(Message.MessageBuilder messageBuilder) {
            this.v2RequestBuilder = messageBuilder;
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
}
