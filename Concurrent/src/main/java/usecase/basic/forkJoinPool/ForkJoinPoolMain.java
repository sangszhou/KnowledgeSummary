package usecase.basic.forkJoinPool;

import java.util.concurrent.ForkJoinPool;

/**
 * Created by xinszhou on 7/10/16.
 */
public class ForkJoinPoolMain {
    public static void main(String[] args) {

        ForkJoinPool pool = new ForkJoinPool(5);
        Long num = pool.invoke(new MyRecursiveTask(12L));

        System.out.println("result: " + num);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
