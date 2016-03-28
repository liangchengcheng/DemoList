package fwq.hdsx.com.proxy.demo2;

public class MyProxyTest {

    public static void main(String[] args) {

        //不需要执行额外方法的 　
        Italk people1 = new People("湖海散人", "18");
        people1.talk("No ProXY Test");

        System.out.println("-----------------------------");
        //需要执行额外方法的 　　
        TalkProxy talker = new TalkProxy(people1);
        talker.talk("ProXY Test", "七里香");
    }
}