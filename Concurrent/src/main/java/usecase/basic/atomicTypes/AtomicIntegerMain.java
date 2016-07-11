package usecase.basic.atomicTypes;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xinszhou on 7/10/16.
 * <p>
 * more methods:
 * addAndGet()
 * getAndAdd()
 * getAndIncrement()
 * incrementAndGet()
 * <p>
 * decrementAndGet()
 * getAndDecrement()
 */
public class AtomicIntegerMain {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(123);

        int theValue = atomicInteger.get();

        atomicInteger.set(234);
        int expectedValue = 123;
        int newValue = 234;
        atomicInteger.compareAndSet(expectedValue, newValue);

    }
}
