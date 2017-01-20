package com.hyber.sdk.send;

import com.hyber.sdk.domain.Message;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Sender {

    Response send(Message message) throws IOException, URISyntaxException;
    Response send(Message message, boolean closeConnectionAfterSend) throws IOException, URISyntaxException;
}
