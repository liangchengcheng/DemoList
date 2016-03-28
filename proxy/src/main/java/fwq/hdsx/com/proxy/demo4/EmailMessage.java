package fwq.hdsx.com.proxy.demo4;

public class EmailMessage implements MessageHandler {

    @Override
    public void sendMessage(String msg) {
        System.out.println(msg+" 使用短信send!!");
    }
}
