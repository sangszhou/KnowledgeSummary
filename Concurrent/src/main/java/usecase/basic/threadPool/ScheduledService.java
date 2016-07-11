package usecase.basic.threadPool;

import java.util.concurrent.*;

/**
 * Created by xinszhou on 7/10/16.
 *
 *
 * how to use the return value of schedule at fix rate?
 */
public class ScheduledService {
    public static void main(String[] args) {
        // what does this 5 means
        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);

        // schedule one
        ScheduledFuture scheduledFuture = service.schedule((Callable) () -> {
            System.out.println("Executed");
            return "Called";
        }, 5, TimeUnit.SECONDS);

        try {
            System.out.println("result: " + scheduledFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //what if I just leave it here without closing it.
        service.shutdown();

    }
}
