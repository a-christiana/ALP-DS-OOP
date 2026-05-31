package logic;

import model.DummyData;


public class App {
    public static void main(String[] args) throws Exception {
        PoliSystem p = new PoliSystem();
        DummyData.dummyData(p);
        p.start();
    }
}
