package com.hyber.sdk.send;

public final class ErrorResponse extends Response {
    @Override
    public boolean hasErrors() {
        return true;
    }

    ErrorResponse(Integer httpCode, Integer errorCode, String errorText, String rawResponse) {
        super(httpCode, null, errorCode, errorText, rawResponse);
    }
}
