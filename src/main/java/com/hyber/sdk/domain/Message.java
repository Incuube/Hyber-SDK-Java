package com.hyber.sdk.domain;

import com.hyber.sdk.constants.Channels;
import com.hyber.sdk.constants.jsonfields.RequestFields;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public final class Message {

    private String phoneNumber;
    private String extraId;
    private String callbackUrl;
    private Long startTime;
    private String tag;
    private Boolean promotional;
    private List<Channels> channels;
    private ViberOptions viber;
    private PushOptions push;
    private SmsOptions sms;

    Message(String phoneNumber, String extraId, String callbackUrl, Long startTime, String tag, Boolean promotional, List<Channels> channels, ViberOptions viber, PushOptions push, SmsOptions sms) {
        this.phoneNumber = phoneNumber;
        this.extraId = extraId;
        this.callbackUrl = callbackUrl;
        this.startTime = startTime;
        this.tag = tag;
        this.promotional = promotional;
        this.channels = channels;
        this.viber = viber;
        this.push = push;
        this.sms = sms;
    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public static class MessageBuilder {
        private String phoneNumber;
        private String extraId;
        private String callbackUrl;
        private Long startTime;
        private String tag;
        private Boolean promotional;
        private List<Channels> channels;
        private ViberOptions viber;
        private PushOptions push;
        private SmsOptions sms;

        MessageBuilder() {
        }

        public MessageBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public MessageBuilder extraId(String extraId) {
            this.extraId = extraId;
            return this;
        }

        public MessageBuilder callbackUrl(String callbackUrl) {
            this.callbackUrl = callbackUrl;
            return this;
        }

        public MessageBuilder startTime(Long startTime) {
            this.startTime = startTime;
            return this;
        }

        public MessageBuilder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public MessageBuilder promotional(Boolean promotional) {
            this.promotional = promotional;
            return this;
        }

        public MessageBuilder channels(Channels... channels) {
            this.channels = Arrays.asList(channels);
            return this;
        }

        public ViberOptions.ViberOptionsBuilder viber() {
            return ViberOptions.builder(this);
        }

        public PushOptions.PushOptionsBuilder push() {
            return PushOptions.builder(this);
        }

        public SmsOptions.SmsOptionsBuilder sms() {
            return SmsOptions.builder(this);
        }

        MessageBuilder viber(ViberOptions viber) {
            this.viber = viber;
            return this;
        }

        MessageBuilder push(PushOptions push) {
            this.push = push;
            return this;
        }

        MessageBuilder sms(SmsOptions sms) {
            this.sms = sms;
            return this;
        }

        public Message build() {
            return new Message(phoneNumber, extraId, callbackUrl, startTime, tag, promotional, channels, viber, push, sms);
        }

    }

    @Override
    public String toString() {
        JSONObject result = new JSONObject();
        result.put(RequestFields.PHONE_NUMBER, this.phoneNumber);
        result.put(RequestFields.CALLBACK_URL, callbackUrl);
        result.put(RequestFields.TAG, tag);
        result.put(RequestFields.IS_PROMOTIONAL, promotional);
        result.put(RequestFields.EXTRA_ID, extraId);

        if (startTime != null) {
            Date startTimeDate = new Date(startTime);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result.put(RequestFields.START_TIME, sdf.format(startTimeDate));
        }
        if (channels != null) {
            result.put(RequestFields.CHANNELS, getJsonChannels());
            setChannelOptions(result, channels);
        }
        return result.toString();
    }

    private JSONArray getJsonChannels() {
        String[] strChannels = new String[channels.size()];
        for (int i = 0; i < channels.size(); i++) {
            strChannels[i] = channels.get(i).name();
        }
        return new JSONArray(strChannels);
    }

    private void setChannelOptions(JSONObject result, List<Channels> channels) {
        JSONObject channelOptionsJson = new JSONObject();
        for (Channels channel : channels) {
            switch (channel) {
                case push:
                    channelOptionsJson.put(channel.name(), push.toJson());
                    break;
                case viber:
                    channelOptionsJson.put(channel.name(), viber.toJson());
                    break;
                case sms:
                    channelOptionsJson.put(channel.name(), sms.toJson());
                    break;
            }
        }
        result.put(RequestFields.CHANNEL_OPTIONS, channelOptionsJson);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getExtraId() {
        return extraId;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public Long getStartTime() {
        return startTime;
    }

    public String getTag() {
        return tag;
    }

    public Boolean getPromotional() {
        return promotional;
    }

    public List<Channels> getChannels() {
        return channels;
    }

    public ViberOptions getViber() {
        return viber;
    }

    public PushOptions getPush() {
        return push;
    }

    public SmsOptions getSms() {
        return sms;
    }


}
