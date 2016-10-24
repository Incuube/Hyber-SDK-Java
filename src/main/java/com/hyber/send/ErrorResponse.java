package com.hyber.send;

public class ErrorResponse extends Response {
    @Override
    public boolean hasErrors() {
        return true;
    }
}
