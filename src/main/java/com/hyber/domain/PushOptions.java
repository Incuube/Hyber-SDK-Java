package com.hyber.domain;

import org.json.JSONObject;

import static com.hyber.constants.jsonfields.RequestFields.*;

final class PushOptions {

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
        jsonObject.put(TTL, ttl);
        jsonObject.put(TEXT, text);
        jsonObject.put(ACTION, action);
        jsonObject.put(CAPTION, caption);
        jsonObject.put(IMG, img);
        jsonObject.put(PUSH_TITLE, title);
        return jsonObject;
    }

    static PushOptionsBuilder builder(Message.MessageBuilder builder) {
        return new PushOptionsBuilder(builder);
    }

    public static class PushOptionsBuilder {
        private final Message.MessageBuilder v2RequestBuilder;
        private String text;
        private Integer ttl;
        private String img;
        private String caption;
        private String action;
        private String title;

        PushOptionsBuilder(Message.MessageBuilder v2RequestBuilder) {
            this.v2RequestBuilder = v2RequestBuilder;
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
}
