package com.hyber.send;

import com.hyber.domain.Message;

public interface Sender {

    Response send(Message message) throws Exception;
    Response send(Message message, boolean closeConnectionAfterSend) throws Exception;
}
