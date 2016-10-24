package com.hyber;

import com.hyber.domain.sdkmessage.*;
import com.hyber.domain.sdkmessage.channel.Channel;
import com.hyber.domain.sdkmessage.channel.ChannelPush;
import com.hyber.domain.sdkmessage.channel.ChannelSms;
import com.hyber.domain.sdkmessage.channel.ChannelViber;
import com.hyber.exception.InvalidRequestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static com.hyber.ValidTestMessageFields.*;
import static com.hyber.ValidTestSenderFields.*;
import static org.junit.Assert.assertEquals;

public class MessageTest {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void checkConvertToJson() throws ParseException, InvalidRequestException {
        Message msg = generateValidMessage();
        long startTime = getStartTime();
        msg.setStartTime(startTime);
        String request = msg.toJson(alphaName).toString();
        System.out.println(request);
        JSONObject actual = new JSONObject(request);
        assertEquals(startTime, sdf.parse(actual.getString("start_time")).getTime() / 1000);
        assertEquals(phoneNumber, (Long) actual.getLong("phone_number"));

        testNonRequiredFields(actual);
        JSONArray actualOrder = actual.getJSONArray("channels");
        testChannels(actualOrder);

        JSONObject actualChannelOptions = actual.getJSONObject("channel_options");
        testViberOptions(actualChannelOptions);
        testPushOptions(actualChannelOptions);

        testTexts(actual);
    }

    private Message generateValidMessage(){
        Message message = new Message(phoneNumber);
        message.setExtraId(extraId);
        message.setIsPromotional(promotional);

        message.setTag(tag);
        message.setCallBackUrl(callbackUrl);

        ChannelPush push = new ChannelPush(pushText, pushTtl);
        push.setImageUrl(pushImgUrl);
        push.setButton(pushActionUrl, pushCaption);

        ChannelViber viber = new ChannelViber(viberText, viberTtl);
        viber.setImageUrl(viberImgUrl);
        viber.setButton(viberActionUrl, viberCaption);
        viber.setIosExpirityText(viberIosExpirityText);

        ChannelSms sms = new ChannelSms(smsText, smsTtl);

        Channel[] channels = new Channel[]{push, viber, sms};
        message.setChannels(channels);
        return message;
    }

    private void testTexts(JSONObject actual) {
        JSONArray actualTexts = actual.getJSONArray("texts");
        assertEquals(pushText, actualTexts.get(0));
        assertEquals(viberText, actualTexts.get(1));
        assertEquals(smsText, actualTexts.get(2));
    }

    private void testViberOptions(JSONObject actualChannelOptions) {
        JSONObject actualViberOptions = actualChannelOptions.getJSONObject("viber");
        assertEquals(viberIosExpirityText, actualViberOptions.getString("ios_expirity_text"));
        assertEquals(viberActionUrl, actualViberOptions.getString("action"));
        assertEquals(viberCaption, actualViberOptions.getString("caption"));
        assertEquals(viberImgUrl, actualViberOptions.getString("img"));

    }

    private void testPushOptions(JSONObject actualChannelOptions) {
        JSONObject actualPushOptions = actualChannelOptions.getJSONObject("push");
        assertEquals(pushActionUrl, actualPushOptions.getString("action"));
        assertEquals(pushCaption, actualPushOptions.getString("caption"));
        assertEquals(pushImgUrl, actualPushOptions.getString("img"));

    }
    private void testChannels(JSONArray actualOrder){
        JSONObject actualPushJSON = (JSONObject) actualOrder.get(0);
        assertEquals(pushName, actualPushJSON.getString("channel"));
        assertEquals(pushTtl, actualPushJSON.getInt("ttl"));

        JSONObject actualViberJSON = (JSONObject) actualOrder.get(1);
        assertEquals(viberName, actualViberJSON.getString("channel"));
        assertEquals(viberTtl, actualViberJSON.getInt("ttl"));

        JSONObject smsJSON = (JSONObject) actualOrder.get(2);
        assertEquals(smsName, smsJSON.getString("channel"));
        assertEquals(smsTtl, smsJSON.getInt("ttl"));
    }

    private void testNonRequiredFields(JSONObject actual){
        assertEquals(extraId, actual.getString("extra_id"));
        assertEquals(promotional, actual.getBoolean("is_promotional"));

        assertEquals(tag, actual.get("tag"));
        assertEquals(callbackUrl, actual.get("callback_url"));

    }



}
