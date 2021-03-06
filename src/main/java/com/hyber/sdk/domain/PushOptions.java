package com.hyber.sdk.domain;

import com.hyber.sdk.constants.jsonfields.RequestFields;
import org.json.JSONObject;

public final class PushOptions {

    private String text;
    private Integer ttl;
    private String img;
    private String caption;
    private String action;
    private String title;

    PushOptions(String text, Integer ttl, String img, String caption, String action, String title) {
        this.text = text;
        this.ttl = ttl;
        this.img = img;
        this.caption = caption;
        this.action = action;
        this.title = title;
    }

    JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(RequestFields.TTL, ttl);
        jsonObject.put(RequestFields.TEXT, text);
        jsonObject.put(RequestFields.ACTION, action);
        jsonObject.put(RequestFields.CAPTION, caption);
        jsonObject.put(RequestFields.IMG, img);
        jsonObject.put(RequestFields.PUSH_TITLE, title);
        return jsonObject;
    }

    static PushOptionsBuilder builder(Message.MessageBuilder builder, PushOptions push) {
        return new PushOptionsBuilder(builder, push);
    }

    public static class PushOptionsBuilder {
        private final Message.MessageBuilder v2RequestBuilder;
        private String text;
        private Integer ttl;
        private String img;
        private String caption;
        private String action;
        private String title;

        PushOptionsBuilder(Message.MessageBuilder v2RequestBuilder, PushOptions push) {
            this.v2RequestBuilder = v2RequestBuilder;
            if (push != null) {
                text = push.getText();
                ttl = push.getTtl();
                img = push.getImg();
                caption = push.getCaption();
                action = push.getAction();
                title = push.getTitle();
            }
        }

        public PushOptionsBuilder text(String text) {
            this.text = text;
            return this;
        }

        public PushOptionsBuilder ttl(Integer ttl) {
            this.ttl = ttl;
            return this;
        }

        public PushOptionsBuilder img(String img) {
            this.img = img;
            return this;
        }

        public PushOptionsBuilder caption(String caption) {
            this.caption = caption;
            return this;
        }

        public PushOptionsBuilder action(String action) {
            this.action = action;
            return this;
        }

        public PushOptionsBuilder title(String title) {
            this.title = title;
            return this;
        }

        private PushOptions build() {
            return new PushOptions(text, ttl, img, caption, action, title);
        }

        public Message.MessageBuilder end() {
            return v2RequestBuilder.push(build());
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

    public String getTitle() {
        return title;
    }
}
