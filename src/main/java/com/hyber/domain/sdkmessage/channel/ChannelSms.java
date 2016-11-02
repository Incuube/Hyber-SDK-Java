package com.hyber.domain.sdkmessage.channel;

import com.hyber.constants.Partners;
import org.json.JSONObject;

import static com.hyber.constants.jsonfields.RequestFields.*;

public class ChannelSms extends Channel {

    private String alphaName;

    public ChannelSms(int ttl, String text, String alphaName) {
        super(ttl);
        setText(text);
        this.alphaName = alphaName;
    }

    @Override
    public Partners getChannel() {
        return Partners.sms;
    }

    public String getAlphaName() {
        return alphaName;
    }


    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TTL, getTtl());
        jsonObject.put(TEXT, getText());
        jsonObject.put(ALPHA_NAME, alphaName);
        return jsonObject;
    }
}
