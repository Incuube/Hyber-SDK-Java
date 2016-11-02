package com.hyber;

import com.hyber.domain.sdkmessage.Message;
import com.hyber.domain.sdkmessage.channel.Channel;
import com.hyber.domain.sdkmessage.channel.ChannelSms;
import com.hyber.exception.InvalidRequestException;
import com.hyber.exception.ValidationErrors;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
    public void failWithNegativeTtl() throws InvalidRequestException {

        Message msg = new Message(380661111111L);
        Channel[] channels = new Channel[]{new ChannelSms(-1, "text", "alpha name")};
        msg.setChannels(channels);

        expectedEx.expect(InvalidRequestException.class);
        expectedEx.expectMessage(ValidationErrors.TTL_NOT_A_POSITIVE_NUMBER.getText());

        msg.validate();
    }

}
