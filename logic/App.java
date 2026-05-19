package logic;

import model.DummyData;


public class App {
    public static void main(String[] args) throws Exception {
        DummyData d = new DummyData();
        PoliSystem p = new PoliSystem();
        d.dummyData(p);
        p.start();
        //testsetesett
    }
}
