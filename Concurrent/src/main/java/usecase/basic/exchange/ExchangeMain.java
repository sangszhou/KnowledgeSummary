package usecase.basic.exchange;

import java.util.concurrent.Exchanger;

/**
 * Created by xinszhou on 7/10/16.
 */
public class ExchangeMain {
    public static void main(String[] args) {
        Exchanger changer = new Exchanger();

        new Thread(new ExchangeRunnable(changer, "A")).start();
        new Thread(new ExchangeRunnable(changer, "B")).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
