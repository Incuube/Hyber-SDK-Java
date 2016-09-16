package com.hyber.domain.sdkmessage.channel;

public class Button {

    private String actionUrl;
    private String caption;

    Button(String actionUrl, String caption) {
        this.actionUrl = actionUrl;
        this.caption = caption;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public String getCaption() {
        return caption;
    }

}
