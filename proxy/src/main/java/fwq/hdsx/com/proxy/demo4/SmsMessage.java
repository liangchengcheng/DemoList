package fwq.hdsx.com.proxy.demo4;

public class SmsMessage implements MessageHandler {

    @Override
    public void sendMessage(String msg) {
        System.out.println("SMS Message :" + msg+" sent !");
    }

}
