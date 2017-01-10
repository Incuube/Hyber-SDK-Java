package com.hyber.sdk.send;

import com.hyber.sdk.domain.Message;

public interface Sender {

    Response send(Message message) throws Exception;
    Response send(Message message, boolean closeConnectionAfterSend) throws Exception;
}
