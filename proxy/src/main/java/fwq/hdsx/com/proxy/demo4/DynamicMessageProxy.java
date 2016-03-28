package fwq.hdsx.com.proxy.demo4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicMessageProxy implements InvocationHandler {

    private static int count;
    private MessageHandler msgHandler;

    public DynamicMessageProxy(MessageHandler handler) {
        msgHandler = handler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

        System.out.println("proxy:" + proxy.getClass());
        System.out.println("method:" + method);

        if (args != null && args.length == 1 && checkMessage((String) args[0])) {
            count++;
            System.out.println("Message sent:" + count);
            return method.invoke(msgHandler, args);
        }
        return null;
    }

    private boolean checkMessage(String msg) {
        return msg != null && msg.length() > 10;
    }

}
