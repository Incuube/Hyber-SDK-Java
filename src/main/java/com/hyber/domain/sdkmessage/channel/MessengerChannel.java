package com.hyber.domain.sdkmessage.channel;

public abstract class MessengerChannel extends Channel {

    private String imageUrl;
    private Button button;

    public MessengerChannel(int ttl) {
        super(ttl);
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
