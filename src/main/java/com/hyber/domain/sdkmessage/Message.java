package com.hyber.domain.sdkmessage;

import com.hyber.constants.Partners;
import com.hyber.domain.hyberrequest.JsonChannel;
import com.hyber.domain.hyberrequest.PushOptions;
import com.hyber.domain.hyberrequest.ViberOptions;
import com.hyber.domain.sdkmessage.channel.Channel;
import com.hyber.domain.sdkmessage.channel.ChannelPush;
import com.hyber.domain.sdkmessage.channel.ChannelViber;
import com.hyber.exception.InvalidRequestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.hyber.constants.Partners.push;
import static com.hyber.constants.Partners.viber;
import static com.hyber.constants.jsonfields.RequestFields.*;
import static com.hyber.exception.ValidationErrors.*;

public class Message {

    private final long phoneNumber;
    private String callBackUrl;
    private String extraId;
    private String tag;
    private Boolean isPromotional;
    private Long startTime;
    private Channel[] channels;


    public Message(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public String getExtraId() {
        return extraId;
    }

    public void setExtraId(String extraId) {
        this.extraId = extraId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean getIsPromotional() {
        return isPromotional;
    }

    public void setIsPromotional(boolean isPromotional) {
        this.isPromotional = isPromotional;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Channel[] getChannels() {
        return channels;
    }

    public void setChannels(Channel[] channels) {
        this.channels = channels;
    }

    private String[] getTexts() throws InvalidRequestException {
        if (channels == null){
            throw new InvalidRequestException(CHANNELS_IS_EMPTY.getText());
        }
        String[] texts = new String[channels.length];
        for (int i = 0; i < channels.length; i++) {
            texts[i] = channels[i].getText();
        }
        return texts;
    }

    public JSONObject toJson(String alphaName) throws InvalidRequestException {
        JSONObject result = new JSONObject();
        result.put(PHONE_NUMBER, this.phoneNumber);
        result.put(TEXTS, getTexts());
        result.put(ALPHA_NAME, alphaName);
        if (callBackUrl != null) {
            result.put(CALLBACK_URL, callBackUrl);
        }
        if (tag != null) {
            result.put(TAG, tag);
        }
        if (isPromotional != null) {
            result.put(IS_PROMOTIONAL, isPromotional);
        }
        if (extraId != null) {
            result.put(EXTRA_ID, extraId);
        }
        if (startTime != null) {
            Date startTimeDate = new Date(startTime * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result.put(START_TIME, sdf.format(startTimeDate));
        }
        result.put(CHANNELS, getJsonChannels());
        setChannelOptions(result, channels);

        return result;
    }

    private void setChannelOptions(JSONObject result, Channel[] order) {

        ViberOptions viberOptions = null;
        PushOptions pushOptions = null;
        for (Channel channel : order) {
            switch (channel.getChannelName()) {
                case viber:
                    viberOptions = ViberOptions.of((ChannelViber) channel);
                    break;
                case push:
                    pushOptions = PushOptions.of((ChannelPush) channel);
                    break;
                default:
                    break;
            }
        }
        JSONObject channelOptionsJson = new JSONObject();
        boolean channelsOptionsExists = false;
        if (viberOptions != null) {
            channelOptionsJson.put(viber.name(), viberOptions.toJson());
            channelsOptionsExists = true;
        }
        if (pushOptions != null) {
            channelOptionsJson.put(push.name(), pushOptions.toJson());
            channelsOptionsExists = true;
        }
        if (channelsOptionsExists) {
            result.put(CHANNEL_OPTIONS, channelOptionsJson);
        }


    }

    private JSONArray getJsonChannels() {
        JSONObject[] jsonChannels = new JSONObject[this.channels.length];
        for (int i = 0; i < this.channels.length; i++) {
            jsonChannels[i] = new JSONObject(new JsonChannel(this.channels[i].getChannelName().name(), this.channels[i].getTtl()));
        }
        return new JSONArray(jsonChannels);
    }

    public void validate() throws InvalidRequestException {
        if (phoneNumber <= 0) {
            throw new InvalidRequestException(PHONE_NUMBER_INCORRECT.getText());
        }
        Set<Partners> uniqueChannels = new HashSet<>();
        if (channels == null || channels.length == 0) {
            throw new InvalidRequestException(CHANNELS_IS_EMPTY.getText());
        }
        for (Channel channel : channels) {
            if (channel.getText() == null) {
                throw new InvalidRequestException(String.format(CHANNEL_TEXT_IS_NULL.getText(), channel.getChannelName()));
            }
            if (channel.getTtl() <= 0)
                throw new InvalidRequestException(TTL_NOT_A_POSITIVE_NUMBER.getText());

            uniqueChannels.add(channel.getChannelName());
        }
        if (uniqueChannels.size() < channels.length) {
            throw new InvalidRequestException(NOT_UNIQUE_CHANNELS.getText());
        }

    }
}
