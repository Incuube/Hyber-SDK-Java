package com.hyber;

import com.hyber.domain.Message;
import com.hyber.send.MessageSender;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.hyber.ValidTestMessageFields.*;
import static com.hyber.ValidTestSenderFields.identifier;
import static com.hyber.ValidTestSenderFields.login;
import static com.hyber.ValidTestSenderFields.password;
import static com.hyber.domain.Partners.*;
import static org.junit.Assert.assertEquals;

public class MessageTest {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void checkConvertToJson() throws ParseException {

        long startTime = getStartTime();
        Message msg = generateValidMessage(startTime);

        String request = msg.toString();
        System.out.println(request);
        JSONObject actual = new JSONObject(request);
        assertEquals(startTime, sdf.parse(actual.getString("start_time")).getTime() / 1000);
        assertEquals(phoneNumber, actual.getString("phone_number"));

        testNonRequiredFields(actual);
        JSONArray actualOrder = actual.getJSONArray("channels");
        testChannels(actualOrder);

        JSONObject actualChannelOptions = actual.getJSONObject("channel_options");
        testViberOptions(actualChannelOptions);
        testSmsOptions(actualChannelOptions);
        testPushOptions(actualChannelOptions);
    }

    private Message generateValidMessage(long startTime) {
        return Message.builder()
                .phoneNumber(phoneNumber)
                .extraId(extraId)
                .promotional(promotional)
                .tag(tag)
                .callbackUrl(callbackUrl)
                .channels(push, viber, sms)
                .startTime(startTime)
                .push()
                .ttl(pushTtl)
                .text(pushText)
                .img(pushImgUrl)
                .action(pushActionUrl)
                .caption(pushCaption)
                .title(pushTitle)
                .end()
                .viber()
                .ttl(viberTtl)
                .text(viberText)
                .img(viberImgUrl)
                .action(viberActionUrl)
                .caption(viberCaption)
                .iosExpirityText(viberIosExpirityText)
                .end()
                .sms()
                .text(smsText)
                .alphaName(alphaName)
                .ttl(smsTtl)
                .end()
                .build();
    }


    private void testViberOptions(JSONObject actualChannelOptions) {
        JSONObject actualViberOptions = actualChannelOptions.getJSONObject("viber");
        assertEquals(viberIosExpirityText, actualViberOptions.getString("ios_expirity_text"));
        assertEquals(viberTtl, actualViberOptions.getInt("ttl"));
        assertEquals(viberText, actualViberOptions.getString("text"));
        assertEquals(viberActionUrl, actualViberOptions.getString("action"));
        assertEquals(viberCaption, actualViberOptions.getString("caption"));
        assertEquals(viberImgUrl, actualViberOptions.getString("img"));

    }

    private void testPushOptions(JSONObject actualChannelOptions) {
        JSONObject actualPushOptions = actualChannelOptions.getJSONObject("push");
        assertEquals(pushTtl, actualPushOptions.getInt("ttl"));
        assertEquals(pushText, actualPushOptions.getString("text"));
        assertEquals(pushActionUrl, actualPushOptions.getString("action"));
        assertEquals(pushCaption, actualPushOptions.getString("caption"));
        assertEquals(pushTitle, actualPushOptions.getString("title"));
        assertEquals(pushImgUrl, actualPushOptions.getString("img"));

    }

    private void testSmsOptions(JSONObject actualChannelOptions) {
        JSONObject actualSmsOptions = actualChannelOptions.getJSONObject("sms");
        assertEquals(smsText, actualSmsOptions.getString("text"));
        assertEquals(smsTtl, actualSmsOptions.getInt("ttl"));
        assertEquals(alphaName, actualSmsOptions.getString("alpha_name"));
    }

    private void testChannels(JSONArray actualOrder) {
        String actualPushJSON = (String) actualOrder.get(0);
        assertEquals(pushName, actualPushJSON);

        String actualViberJSON = (String) actualOrder.get(1);
        assertEquals(viberName, actualViberJSON);

        String smsJSON = (String) actualOrder.get(2);
        assertEquals(smsName, smsJSON);
    }

    private void testNonRequiredFields(JSONObject actual) {
        assertEquals(extraId, actual.getString("extra_id"));
        assertEquals(promotional, actual.getBoolean("is_promotional"));
        assertEquals(tag, actual.get("tag"));
        assertEquals(callbackUrl, actual.get("callback_url"));

    }


}
