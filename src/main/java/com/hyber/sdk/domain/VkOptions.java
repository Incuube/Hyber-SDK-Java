package com.hyber.sdk.domain;

import com.hyber.sdk.constants.jsonfields.RequestFields;
import org.json.JSONObject;

public final class VkOptions {
    private Integer ttl;
    private String data;
    private String template;
    private String text;

    public VkOptions(Integer ttl, String data, String template, String text) {
        this.ttl = ttl;
        this.data = data;
        this.template = template;
        this.text = text;
    }

    JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(RequestFields.TTL, ttl);
        jsonObject.put(RequestFields.DATA, data);
        jsonObject.put(RequestFields.TEMPLATE, template);
        jsonObject.put(RequestFields.TEXT, text);
        return jsonObject;
    }

    public static class VkOptionsBuilder {
        private final Message.MessageBuilder v2RequestBuilder;
        private Integer ttl;
        private String data;
        private String template;
        private String text;

        public VkOptionsBuilder(Message.MessageBuilder v2RequestBuilder, VkOptions vk) {
            this.v2RequestBuilder = v2RequestBuilder;
            if (vk != null) {
                ttl = vk.getTtl();
                data = vk.getData();
                template = vk.getTemplate();
                text = vk.getText();
            }
        }

        public VkOptionsBuilder ttl(Integer ttl) {
            this.ttl = ttl;
            return this;
        }

        public VkOptionsBuilder data(String data) {
            this.data = data;
            return this;
        }

        public VkOptionsBuilder template(String template) {
            this.template = template;
            return this;
        }

        public VkOptionsBuilder text(String text) {
            this.text= text;
            return this;
        }

        private VkOptions build() {
            return new VkOptions(ttl, data, template, text);
        }

        public Message.MessageBuilder end() {
            return v2RequestBuilder.vk(build());
        }
    }

    static VkOptionsBuilder builder(Message.MessageBuilder builder, VkOptions vk) {
        return new VkOptionsBuilder(builder, vk);
    }

    public Integer getTtl() {
        return ttl;
    }

    public String getData() {
        return data;
    }

    public String getTemplate() {
        return template;
    }

    public String getText() {
        return text;
    }
}
