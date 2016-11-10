package com.hyber;

public  final class ValidTestMessageFields {

    protected final static Long phoneNumber = 380661111111L;
    protected final static String extraId = "extraId";
    protected final static boolean promotional = false;
    protected final static String tag = "Tag Example";
    protected final static String callbackUrl = "http://localhost/http-receiver/api/%d/messages/create";
   
    protected final static int smsTtl = 15;
    protected final static String smsText = "Sms text";
    protected final static String smsName = "sms";
    protected final static String alphaName = "TEST";

    protected final static int viberTtl = 15;
    protected final static String viberText = "Viber text";
    protected final static String viberIosExpirityText = "Viber ios expirity text";
    protected final static String viberImgUrl = "http://localhost/viber/img/img.jpg";
    protected final static String viberActionUrl = "http://localhost/viber/action";
    protected final static String viberCaption = "Viber caption";
    protected final static String viberName = "viber";
   
    protected final static int  pushTtl = 15;
    protected final static String  pushText = "Push text";
    protected final static String pushImgUrl = "http://localhost/push/img/img.jpg";
    protected final static String pushActionUrl = "http://localhost/push/action";
    protected final static String pushCaption = "Viber caption";
    protected final static String pushName = "push";

    protected static long getStartTime(){
        return System.currentTimeMillis();
    }


    private ValidTestMessageFields(){

    }

}
