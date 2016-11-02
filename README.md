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
Message have mandatory and optional fields

- Create message with phone (mandatory)
```java
long phonenumber = 380000000000L;
Message message = new Message(phonenumber);
```

Optional parameters
```java
String url = "http://test.com";
String extraId = "ae6912895ff768da44";
String tag = "testTag";
boolean promotional = false;
long startTime = 1473767710;

//url where Hyber platform will send delivery report
message.setCallBackUrl(url);           

//some identifier from external system
message.setExtraId(extraId);            

//on Hyber portal you can filter statistics by tag
message.setTag(tag);                    

//whether or not your message is promotional (advertising)
message.setIsPromotional(promotional);  

//time to start send message, in seconds, UNIX format
message.setStartTime(startTime);        
```

You may add whatever available channels you want, however if specific channel is not enabled for you,
there will be no delivery via this channel. If sms channel is present, it must be last in chain.

For each channel mandatory parameter is TTL
(time-to-live, how long we try to send message via this channel before considering it expired)
Channels will be used in same order you added them.

Create specific message for partner push
```java
//time to wait before send to next partner
int pushTtlSeconds = 15;

//text for push message
String pushText = "text for push message";   

//link to image in push message
String imageUrl = "http://test.img.com";   

//path to go on button press  
String actionUrl = "http://google.com";  

//text on button    
String caption =  "go to google";           
 
//title of push message
 String title = "HyberClient"
 
//mandatory 
ChannelPush channelPush = new ChannelPush(pushTtlSeconds); 

//optional for push
channelPush.setText(pushText);

//optional, url for image that will displayed in push message
channelPush.setImageUrl(imageUrl);       
    
//optional, button in message that will displayed in push message    
channelPush.setButton(actionUrl, caption);  
 
//optional, title of message
channelPush.setTitle(title);
```
Create specific message for partner viber
```java
//time to wait before send to next partner
int viberTtlSeconds = 15;                             

//text for viber message
String viberText = "text for viber message";           

//text for IoS if message delivered after ttl expirity
String iosExpirityText = "text for viber ios devices"; 

//link to image in viber message
String imageUrl = "http://test.img.com";               

//path to go on button press
String actionUrl = "http://google.com";                

//text on button
String caption =  "go to google";                     

//mandatory
ChannelViber channelViber = new ChannelViber(viberTtlSeconds); 

//optional
channelViber.setText(viberText);

//optional, can be set only with button
channelViber.setImageUrl(imageUrl);                    

//optional, set the button in message
channelViber.setButton(actionUrl, caption);            

//optional, set the expirity text for ios devices
channelViber.setIosExpirityText(iosExpirityText);      
```

Create specific channel for partner sms
```java
int smsTtlSeconds = 15;
String smsText = "text for sms message";
String alphaName = "HyberClient";
ChannelSms channelSms = new ChannelSms(smsText, smsTtlSeconds, alphaName); //mandatory
```

Build channels order
```java
//mandatory, order from left to right direction (left is first partner, right is last)
Channel[] channels = new Channel[]{channelPush, channelViber, channelSms}; 

message.setChannels(channels); 
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

However, if the message was not sent at all - you will receive Exception

#### Exceptions

| Name                                        | Description               |
|---------------------------------------------|---------------------------|
| com.hyber.exception.InvalidRequestException | Not correct data was used |
| Exception                                   | Any other exception, except not correct data|

