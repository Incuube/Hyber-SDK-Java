package com.hyber;

import com.hyber.domain.sdkmessage.Message;
import com.hyber.domain.sdkmessage.channel.Channel;
import com.hyber.domain.sdkmessage.channel.ChannelSms;
import com.hyber.exception.InvalidRequestException;
import com.hyber.exception.ValidationErrors;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.hyber.ValidTestMessageFields.smsName;

public class ValidationTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test()
    public void failWithIncorrectPhone() throws InvalidRequestException {

        Message msg = new Message(-1);

        expectedEx.expect(InvalidRequestException.class);
        expectedEx.expectMessage(ValidationErrors.PHONE_NUMBER_INCORRECT.getText());

        msg.validate();
    }

    @Test()
    public void failWithoutChannels() throws InvalidRequestException {

        Message msg = new Message(380661111111L);
        msg.setChannels(new Channel[0]);

        expectedEx.expect(InvalidRequestException.class);
        expectedEx.expectMessage(ValidationErrors.CHANNELS_IS_EMPTY.getText());

        msg.validate();
    }

    @Test()
    public void failWithoutChannelText() throws InvalidRequestException {

        Message msg = new Message(380661111111L);
        Channel[] channels = new Channel[]{new ChannelSms(null, 15)};
        msg.setChannels(channels);

        expectedEx.expect(InvalidRequestException.class);
        expectedEx.expectMessage(String.format(ValidationErrors.CHANNEL_TEXT_IS_NULL.getText(), smsName));

        msg.validate();
    }

    @Test()
    public void failWithNegativeTtl() throws InvalidRequestException {

        Message msg = new Message(380661111111L);
        Channel[] channels = new Channel[]{new ChannelSms("Sms text", -1)};
        msg.setChannels(channels);

        expectedEx.expect(InvalidRequestException.class);
        expectedEx.expectMessage(ValidationErrors.TTL_NOT_A_POSITIVE_NUMBER.getText());

        msg.validate();
    }

}
