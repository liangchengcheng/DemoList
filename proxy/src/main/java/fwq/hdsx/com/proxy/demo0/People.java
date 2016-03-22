package fwq.hdsx.com.proxy.demo0;

/**
 * Created by lcc on 16/3/22.
 */
public class People implements buy_car {

    private int cash;
    private String username;


    public int getCash() {
        return cash;
    }


    public void setCash(int cash) {
        this.cash = cash;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String buy_car() {
        return username +"买了一台新车";
    }
}
