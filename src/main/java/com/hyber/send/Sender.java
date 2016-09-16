package com.hyber.send;

import com.hyber.domain.sdkmessage.Message;

public interface Sender {

    Response send(Message message) throws Exception;
}
