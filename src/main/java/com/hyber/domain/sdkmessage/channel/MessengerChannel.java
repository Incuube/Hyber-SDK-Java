package com.hyber.domain.sdkmessage.channel;

public abstract class MessengerChannel extends Channel {

    private String imageUrl;
    private Button button;

    public MessengerChannel(String text, int ttl) {
        super(text, ttl);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(String actionUrl, String caption) {
        this.button = new Button(actionUrl, caption);
    }
}
