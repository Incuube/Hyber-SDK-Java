package com.hyber.sdk.domain;

import org.json.JSONObject;

import static com.hyber.sdk.constants.jsonfields.RequestFields.*;

final class SmsOptions {

    private String text;
    private Integer ttl;
    private String alphaName;

    SmsOptions(String text, Integer ttl, String alphaName) {
        this.text = text;
        this.ttl = ttl;
        this.alphaName = alphaName;
    }

    JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TTL, ttl);
        jsonObject.put(TEXT, text);
        jsonObject.put(ALPHA_NAME, alphaName);
        return jsonObject;
    }

    static SmsOptionsBuilder builder(Message.MessageBuilder builder) {
        return new SmsOptionsBuilder(builder);
    }


  public static class SmsOptionsBuilder {
        private String text;
        private Integer ttl;
        private String alphaName;
        private final Message.MessageBuilder v2RequestBuilder;

        SmsOptionsBuilder(Message.MessageBuilder v2RequestBuilder) {
            this.v2RequestBuilder = v2RequestBuilder;
        }


        public SmsOptionsBuilder text(String text) {
            this.text = text;
            return this;
        }

        public SmsOptionsBuilder ttl(Integer ttl) {
            this.ttl = ttl;
            return this;
        }

        public SmsOptionsBuilder alphaName(String alphaName) {
            this.alphaName = alphaName;
            return this;
        }

        SmsOptions build() {
            return new SmsOptions(text, ttl, alphaName);
        }

        public Message.MessageBuilder end() {
            return v2RequestBuilder.sms(build());
        }

    }
}
