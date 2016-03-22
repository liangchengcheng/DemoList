package fwq.hdsx.com.proxy.demo0;


public class buy_car_proxy implements buy_car {

    private People people;

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public String buy_car() {
        if (people.getCash() > 2000) {
            return people.getUsername() + "花" + people.getCash() + "块买了新车,交易结束!";
        } else {
            return people.getUsername() + "金钱不够，请继续比赛!";
        }
    }
}
