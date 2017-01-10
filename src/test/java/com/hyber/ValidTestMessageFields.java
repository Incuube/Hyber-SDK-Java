package com.hyber;

final class ValidTestMessageFields {

    final static String phoneNumber = "380000000000";
     final static String extraId = "extraId";
     final static boolean promotional = false;
     final static String tag = "Tag Example";
     final static String callbackUrl = "http://localhost.com/http-receiver/api/messages/create";

     final static int smsTtl = 15;
     final static String smsText = "Sms text";
     final static String smsName = "sms";
     final static String alphaName = "AN";

     final static int viberTtl = 15;
     final static String viberText = "Viber text";
     final static String viberIosExpirityText = "Viber ios expirity text";
     final static String viberImgUrl = "http://localhost.com/viber/img/img.jpg";
     final static String viberActionUrl = "http://localhost.com/viber/action";
     final static String viberCaption = "Viber caption";
     final static String viberName = "viber";

     final static int pushTtl = 15;
     final static String pushText = "Push text";
     final static String pushImgUrl = "http://localhost.com/push/img/img.jpg";
     final static String pushActionUrl = "http://localhost.com/push/action";
     final static String pushCaption = "Push caption";
     final static String pushName = "push";
     final static String pushTitle = "Push title";

     static long getStartTime() {
        return System.currentTimeMillis();
    }


    private ValidTestMessageFields() {

    }

}
