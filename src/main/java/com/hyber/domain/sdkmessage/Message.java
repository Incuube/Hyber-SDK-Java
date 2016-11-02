package com.hyber.domain.sdkmessage;

import com.hyber.constants.Partners;
import com.hyber.domain.sdkmessage.channel.Channel;
import com.hyber.exception.InvalidRequestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    public JSONObject toJson() throws InvalidRequestException {
        JSONObject result = new JSONObject();
        result.put(PHONE_NUMBER, this.phoneNumber);
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

        JSONObject channelOptionsJson = new JSONObject();
        for (Channel channel : order) {
            channelOptionsJson.put(channel.getChannel().name(), channel.toJson());
        }
        result.put(CHANNEL_OPTIONS, channelOptionsJson);
    }

    private JSONArray getJsonChannels() {
        String[] partners = new String[this.channels.length];
        for (int i = 0; i < this.channels.length; i++) {
            partners[i]= this.channels[i].getChannel().name();
        }
        return new JSONArray(partners);
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
            if (channel.getTtl() <= 0)
                throw new InvalidRequestException(TTL_NOT_A_POSITIVE_NUMBER.getText());

            uniqueChannels.add(channel.getChannel());
        }
        if (uniqueChannels.size() < channels.length) {
            throw new InvalidRequestException(NOT_UNIQUE_CHANNELS.getText());
        }

    }
}
