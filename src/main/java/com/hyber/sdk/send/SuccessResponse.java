package com.hyber.sdk.send;

public final class SuccessResponse extends Response {

    SuccessResponse(Integer httpCode, Long messageId) {
        super(httpCode, messageId, null, null);
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}
