package com.hyber.sdk.send;

public final class ErrorResponse extends Response {
    @Override
    public boolean hasErrors() {
        return true;
    }
}