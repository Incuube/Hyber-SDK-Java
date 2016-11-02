package com.hyber;

import com.hyber.send.HttpSender;
import com.hyber.send.MessageSender;
import org.junit.Test;
import javax.xml.bind.DatatypeConverter;
import static com.hyber.ValidTestSenderFields.*;
import static org.junit.Assert.assertEquals;

public class SenderTest {

    @Test
    public void checkDefaultTimeouts() {
        HttpSender httpSender = new MessageSender(login, password, identifier);
        assertEquals((Integer) 60, httpSender.getConnectionTimeout());
        assertEquals((Integer) 60, httpSender.getReadTimeout());
    }

    @Test
    public void checkSetTimeouts() {
        HttpSender httpSender = new MessageSender(login, password, identifier);
        httpSender.setReadTimeout(120);
        httpSender.setConnectionTimeout(120);
        assertEquals((Integer) 120, httpSender.getConnectionTimeout());
        assertEquals((Integer) 120, httpSender.getReadTimeout());
    }

    @Test
    public void checkBasic64Encoding() {
        String login = "test";
        String password = "test";
        String auth = "Basic " + DatatypeConverter.printBase64Binary((login + ":" + password).getBytes());
        assertEquals("Basic dGVzdDp0ZXN0", auth);

    }
}
