package usecase.basic.cyclicBarrier;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by xinszhou on 7/10/16.
 */
public class CyclicBarrierRunnable implements Runnable {
    CyclicBarrier barrier1 = null;
    CyclicBarrier barrier2 = null;

    public CyclicBarrierRunnable(CyclicBarrier barrier1, CyclicBarrier barrier2) {
        this.barrier1 = barrier1;
        this.barrier2 = barrier2;
    }


    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " waiting at barrier 1");
            barrier1.await();

            Thread.sleep(1000);
            //currentThread 是怎么实现的
            System.out.println(Thread.currentThread().getName() + " waiting at the barrier 2");
            barrier2.await();

            System.out.println(Thread.currentThread().getName() + " completed!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
