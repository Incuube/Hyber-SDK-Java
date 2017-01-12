package com.hyber.sdk.send;

public final class ErrorResponse extends Response {
    @Override
    public boolean hasErrors() {
        return true;
    }

    ErrorResponse(int httpCode, int errorCode, String errorText) {
        super(httpCode, null, errorCode, errorText);
    }
}
