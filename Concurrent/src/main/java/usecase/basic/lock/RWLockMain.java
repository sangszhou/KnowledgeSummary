package usecase.basic.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by xinszhou on 7/10/16.
 *
 * its an advanced thread lock mechanism, it allows multi resource
 * to read the lock but only one write it, at a time
 *
 *
 * ? how to put writer at higher priority than read ?
 */
public class RWLockMain {
    public static void main(String[] args) {
        ReadWriteLock lock = new ReentrantReadWriteLock();

        lock.readLock().lock();

        // multiple readers could enter this section if not locked for writing
        // and no writers could enter to lock for writing

        lock.readLock().unlock();

        lock.writeLock().lock();
        // only one writer could enter this section,
        // and only if no thread are reading
        lock.writeLock().unlock();
    }
}
