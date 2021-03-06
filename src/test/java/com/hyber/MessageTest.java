package com.hyber;

import com.hyber.sdk.domain.Message;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.hyber.ValidTestMessageFields.*;
import static com.hyber.sdk.constants.Channels.push;
import static com.hyber.sdk.constants.Channels.sms;
import static com.hyber.sdk.constants.Channels.viber;
import static com.hyber.sdk.constants.Channels.vk;
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
        assertEquals(Math.round(startTime), Math.round(sdf.parse(actual.getString("start_time")).getTime()));
        assertEquals(phoneNumber, actual.getString("phone_number"));

        testNonRequiredFields(actual);
        JSONArray actualOrder = actual.getJSONArray("channels");
        testChannels(actualOrder);

        JSONObject actualChannelOptions = actual.getJSONObject("channel_options");
        testViberOptions(actualChannelOptions);
        testSmsOptions(actualChannelOptions);
        testPushOptions(actualChannelOptions);
        testVkOptions(actualChannelOptions);
    }

    private Message generateValidMessage(long startTime) {
        return Message.builder()
                .phoneNumber(phoneNumber)
                .extraId(extraId)
                .promotional(promotional)
                .tag(tag)
                .callbackUrl(callbackUrl)
                .channels(push, viber, vk, sms)
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
                .vk()
                    .ttl(vkTtl)
                    .text(vkText)
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

    private void testVkOptions(JSONObject actualChannelOptions) {
        JSONObject actualVkOptions = actualChannelOptions.getJSONObject("vk");
        assertEquals(vkTtl, actualVkOptions.getInt("ttl"));
        assertEquals(vkText, actualVkOptions.getString("text"));
    }

    private void testChannels(JSONArray actualOrder) {
        String actualPushJSON = (String) actualOrder.get(0);
        assertEquals(pushName, actualPushJSON);

        String actualViberJSON = (String) actualOrder.get(1);
        assertEquals(viberName, actualViberJSON);

        String actualVkJson = (String) actualOrder.get(2);
        assertEquals(vkName, actualVkJson);

        String smsJSON = (String) actualOrder.get(3);
        assertEquals(smsName, smsJSON);
    }

    private void testNonRequiredFields(JSONObject actual) {
        assertEquals(extraId, actual.getString("extra_id"));
        assertEquals(promotional, actual.getBoolean("is_promotional"));
        assertEquals(tag, actual.get("tag"));
        assertEquals(callbackUrl, actual.get("callback_url"));

    }


}
