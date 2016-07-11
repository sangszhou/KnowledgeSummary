package usecase.basic.atomicTypes;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by xinszhou on 7/10/16.
 */
public class AtomicBooleanMain {
    public static void main(String[] args) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);

        boolean value = atomicBoolean.get();

        atomicBoolean.set(false);

        //get old and set new
        boolean oldValue = atomicBoolean.getAndSet(false);


        boolean expectedValue = true;
        boolean newValue      = false;

        // if current value is equal to expected value, newValue will be set
        //
        boolean wasNewValueSet = atomicBoolean.compareAndSet(
                expectedValue, newValue);

    }
}
