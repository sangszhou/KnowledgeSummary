package usecase.basic.unsafe;

/**
 * Created by xinszhou on 7/16/16.
 */
public class WaitAndNotifyDemo {
    static class MyThread extends Thread {

        public void run() {
            synchronized (this) {
                System.out.println("before notify");
                notify();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("after notify");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new MyThread();

        synchronized (thread) {
            thread.start();
            Thread.sleep(300);
            System.out.println("before wait");
            thread.wait();
            System.out.println("after wait");
        }
    }

}
