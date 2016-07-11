###Semaphore

semaphore comes with two method, acquire and release, it means that at most N thread can pass acquire at the same time,
without release is called, where N is the permits the semaphore initialized with.

`fairness`
No guarantees are made about the fairness of the threads acquiring permits from semaphore. That is there is no guarantee
the first thread to call acquire() is also the first thread to obtain the permits.

If you want to enforce fairness, the semaphore has a constructor that takes a boolean telling if the semaphore
should enfore fairness. Enforcing fairness comes at the performance/concurrency penalty, so don't enable it if not
needed.

In spite of acquire, semaphore also has a method called tryAcquire, unlike acquire, tryAcquire will return true/false
if success/failed to obtain the permits within given time.

