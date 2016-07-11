package usecase.basic.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by xinszhou on 7/10/16.
 *
 * 从这个例子中应该看出打印出来的 log 是整齐排列好的
 * 某个 thread 的 waiting at barrier1 和 waiting at the barrier2 不能连续出现
 */
public class TwoThreadRun {

    public static void main(String[] args) {
        
    	CyclicBarrier barrier1 = new CyclicBarrier(2);
        CyclicBarrier barrier2 = new CyclicBarrier(2);

        new Thread(new CyclicBarrierRunnable(barrier1, barrier2)).start();
        new Thread(new CyclicBarrierRunnable(barrier1, barrier2)).start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
