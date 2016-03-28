package fwq.hdsx.com.proxy.demo4;

import java.lang.reflect.Proxy;


public class MainClass {

    private static void runProxy(MessageHandler handler) {
        handler.sendMessage("message for test");
    }

    public static void main(String[] args) {
        //基本的调用
        runProxy(new EmailMessage());

        System.out.println("++++++++++++++++Proxy++++++++++++++++++");
        //使用代理模式的调用
        runProxy(new MessageProxy());
    }

    /**
     * 动态代理的调用
     */
    public static void DynamicMethod(){

        //邮件方式
        MessageHandler handler = new EmailMessage();
        runProxy(handler);
        MessageHandler proxy = (MessageHandler) Proxy.newProxyInstance(
                MessageHandler.class.getClassLoader(),
                new Class[] { MessageHandler.class }, new DynamicMessageProxy(
                        handler));
        runProxy(proxy);


        System.out.println("++++++++++++++++++++++++++++++++++");


        // 短信方式
        handler = new SmsMessage();
        runProxy(handler);
        proxy = (MessageHandler) Proxy.newProxyInstance(MessageHandler.class
                        .getClassLoader(), new Class[] { MessageHandler.class },
                new DynamicMessageProxy(handler));

        runProxy(proxy);
    }

}
