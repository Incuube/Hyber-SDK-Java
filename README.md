# Hyber-SDK-Java
Easy way to integrate Java-powered system with Hyber platform

[![Build Status](https://travis-ci.org/Incuube/Hyber-SDK-Java.svg)](https://travis-ci.org/Incuube/Hyber-SDK-Java)

## Usage example

import com.hyber.*

First, you need to create service that will send your messages.
All this parameters are mandatory. They are provided for each Hyber customer and rarely change

```java
String login = "test";
String password = "pass";
int identifier = 5;
HttpSender sender = new MessageSender(login, password, identifier);
```

You may specify some addition sender parameters, however they are not mandatory
```java
int timeout = 60;

//connection timeout, default is 60
sender.setConnectionTimeout(timeout);

//read timeout, default is 60
sender.setReadTimeout(timeout);       
```

Build message that will sent to Hyber
```java
Message message = Message.builder()
                .phoneNumber("3800000000") //mandatory, String 
                .extraId("Extra id") //optional, String
                .promotional(false) // optional, boolean
                .tag("Some tag") // optional 
                .callbackUrl("http://callbackurl.com") //optional, String
                .channels(push, viber, sms) //mandatory, Partners enum values,
                .startTime(startTime) //time to start send message, in millis, UNIX format 
                .push() //start buliding options of push channel
                    .ttl(15) //mandatory, time to wait before send to next partner,in sec, Integer
                    .text("Some text for push") //optional, String
                    .img("http://localhost.com/push/img/img.jpg") //optional, String, valid URL
                    .action("http://localhost.com/push/action")//optional, String, valid URL
                    .caption("Push caption") //optional, String, text pon button
                    .title("Push title") //optional, String
                    .end()
                .viber()
                    .ttl(15) //mandatory, time to wait before send to next partner,in sec, Integer
                    .text("Viber text") //optional, String
                    .img("http://localhost.com/viber/img/img.jpg") //optional, String, valid URL
                    .action("http://localhost.com/viber/action")//optional, String, valid URL
                    .caption("Viber caption") //optional, String, text pon button
                    .iosExpirityText("Viber ios expirity text"), optional, String
                    .end()
                .sms()
                    .text("Some text for SMS") //mandatory for sms, String
                    .alphaName("Alpha name") //mandatory, String
                    .ttl(15) //mandatory, Integer
                    .end()
                .build();

```

## Send message

If your data is malformed, exception will be thrown. Otherwise, message will be sent

```java
Response response = sender.send(message); //(throws Exception)
```

You may receive SuccessResponse or ErrorResponse
```java
if (response instanceof SuccessResponse) {
    System.out.println("messageId=" + response.getMessageId());
} else if (response instanceof ErrorResponse) {
    System.out.println("httpCode=" + response.getHttpCode());
    System.out.println("errorCode=" + response.getErrorCode());
    System.out.println("errorText=" + response.getErrorText());
}
```

