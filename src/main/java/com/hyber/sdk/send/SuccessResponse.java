package com.hyber.sdk.send;

public final class SuccessResponse extends Response {

    SuccessResponse(Integer httpCode, Long messageId, String rawResponse) {
        super(httpCode, messageId, null, null, rawResponse);
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}
