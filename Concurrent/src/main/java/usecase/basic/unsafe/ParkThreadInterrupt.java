package usecase.basic.unsafe;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by xinszhou on 7/16/16.
 *
 * park 线程被 unpark 的条件之一 线程被 interrupt
 *
 */
public class ParkThreadInterrupt {

    static class MyThread extends Thread {
        Object object;

        public MyThread(Object object) {
            this.object = object;
        }

        public void run() {
            System.out.println("before interrupt");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Thread thread = (Thread) object;
            thread.interrupt();

            System.out.println("after interrupt");
        }
    }

    public static void main(String[] args) {

        MyThread myThread = new MyThread(Thread.currentThread());

        myThread.start();

        System.out.println("before park");

        LockSupport.park("Give your info");

        System.out.println("after park");

    }
}
