package com.hyber;

import com.hyber.sdk.send.MessageSender;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;

import static com.hyber.ValidTestSenderFields.*;
import static org.junit.Assert.assertEquals;

public class SenderTest {

    @Test
    public void checkDefaultTimeouts() {
        MessageSender sender = new MessageSender(login, password, identifier);
        assertEquals((Integer) 60, sender.getConnectionTimeoutSec());
        assertEquals((Integer) 60, sender.getReadTimeoutSec());
    }

    @Test
    public void checkSetTimeouts() {
        MessageSender httpSender = new MessageSender(login, password, identifier);
        httpSender.setReadTimeoutSec(120);
        httpSender.setConnectionTimeoutSec(120);
        assertEquals((Integer) 120, httpSender.getConnectionTimeoutSec());
        assertEquals((Integer) 120, httpSender.getReadTimeoutSec());
    }

    @Test
    public void checkBasic64Encoding() {
        String auth = "Basic " + DatatypeConverter.printBase64Binary((login + ":" + password).getBytes());
        assertEquals("Basic bG9naW46cGFzc3dvcmQ=", auth);

    }
}
