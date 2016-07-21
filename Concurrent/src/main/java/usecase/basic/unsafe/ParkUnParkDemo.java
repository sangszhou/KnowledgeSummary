package usecase.basic.unsafe;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by xinszhou on 7/16/16.
 *
 * unpark 和 park 是对某一个线程而言的, park 不需要指定线程, 因为 park 的就是当前线程
 * unpark 需要指定线程是谁, 这样才会知道具体是把哪个线程唤醒
 * 被 park 处理后的线程处于线程状态中的哪一个呢?
 */
public class ParkUnParkDemo {
    static class MyThread extends java.lang.Thread {
        Object object;

        public MyThread(Object object) {
            this.object = object;
        }

        public void run() {
            System.out.println("before unpark");

            try {
                java.lang.Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 获取blocker
            System.out.println("Blocker info " + LockSupport.getBlocker((java.lang.Thread) object));
            // 释放许可
            LockSupport.unpark((java.lang.Thread) object);

            // 再次获取blocker
            // 这个应该返回 null 但是实际上返回的仍然是原始值 ParkAndUnparkDemo
            // 但是加上 Thread.sleep 以后就可以看到 null 了
            // 所以通过 unpark 后处理还是有点不靠谱, 比 lock 少了一个相对性
//            try {
//                Thread.sleep(300);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("Blocker info " + LockSupport.getBlocker((java.lang.Thread) object));
            System.out.println("after unpark");
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread(java.lang.Thread.currentThread());
        myThread.start();
        System.out.println("before park");
        // 获取许可
        LockSupport.park("ParkAndUnparkDemo");
        System.out.println("after park");
    }

}
