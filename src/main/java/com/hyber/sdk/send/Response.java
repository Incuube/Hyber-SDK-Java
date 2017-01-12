package com.hyber.sdk.send;

public abstract class Response {

    private Integer httpCode;
    private Long messageId;
    private Integer errorCode;

    Response(Integer httpCode, Long messageId, Integer errorCode, String errorText) {
        this.httpCode = httpCode;
        this.messageId = messageId;
        this.errorCode = errorCode;
        this.errorText = errorText;
    }

    private String errorText;
    
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

    void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
