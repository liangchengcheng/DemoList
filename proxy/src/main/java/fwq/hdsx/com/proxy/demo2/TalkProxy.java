package fwq.hdsx.com.proxy.demo2;

public class TalkProxy implements Italk {

    Italk talker;

    public TalkProxy(Italk talker) {
        super();
        this.talker = talker;
    }

    //method1
    public void talk(String msg) {
        talker.talk(msg);
    }

    //method2
    public void talk(String msg, String singname) {
        talker.talk(msg);
        sing(singname);
    }

    private void sing(String singname) {
        System.out.println("唱歌：" + singname);
    }
}

