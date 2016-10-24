package com.hyber.exception;

public enum ValidationErrors {

    PHONE_NUMBER_INCORRECT("Phone number incorrect!"),
    CHANNELS_IS_EMPTY("Channels can't be empty"),
    CHANNEL_TEXT_IS_NULL("Text for channel %s can't be null!"),
    NOT_UNIQUE_CHANNELS("Channels must be unique"),
    TTL_NOT_A_POSITIVE_NUMBER("Ttl must be > 0");

    private String text;

    ValidationErrors(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
