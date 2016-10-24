package com.hyber.send;

public abstract class Response {

    private Long messageId;
    private Integer httpCode;
    private String errorText;
    private Integer errorCode;

    public abstract boolean hasErrors();

    public Long getMessageId() {
        return messageId;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public String getErrorText() {
        return errorText;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
