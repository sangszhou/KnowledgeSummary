/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file:
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/licenses/publicdomain
 */

package concurrent;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Provides default implementations of {@link java.util.concurrent.ExecutorService}
 * execution methods. This class implements the <tt>submit</tt>,
 * <tt>invokeAny</tt> and <tt>invokeAll</tt> methods using a
 * {@link java.util.concurrent.RunnableFuture} returned by <tt>newTaskFor</tt>, which defaults
 * to the {@link java.util.concurrent.FutureTask} class provided in this package.  For example,
 * the implementation of <tt>submit(Runnable)</tt> creates an
 * associated <tt>RunnableFuture</tt> that is executed and
 * returned. Subclasses may override the <tt>newTaskFor</tt> methods
 * to return <tt>RunnableFuture</tt> implementations other than
 * <tt>FutureTask</tt>.
 *
 * <p> <b>Extension example</b>. Here is a sketch of a class
 * that customizes {@link java.util.concurrent.ThreadPoolExecutor} to use
 * a <tt>CustomTask</tt> class instead of the default <tt>FutureTask</tt>:
 * <pre>
 * public class CustomThreadPoolExecutor extends ThreadPoolExecutor {
 *
 *   static class CustomTask&lt;V&gt; implements RunnableFuture&lt;V&gt; {...}
 *
 *   protected &lt;V&gt; RunnableFuture&lt;V&gt; newTaskFor(Callable&lt;V&gt; c) {
 *       return new CustomTask&lt;V&gt;(c);
 *   }
 *   protected &lt;V&gt; RunnableFuture&lt;V&gt; newTaskFor(Runnable r, V v) {
 *       return new CustomTask&lt;V&gt;(r, v);
 *   }
 *   // ... add constructors, etc.
 * }
 * </pre>
 * @since 1.5
 * @author Doug Lea
 */
public abstract class AbstractExecutorService implements ExecutorService {

    /**
     * Returns a <tt>RunnableFuture</tt> for the given runnable and default
     * value.
     *
     * @param runnable the runnable task being wrapped
     * @param value the default value for the returned future
     * @return a <tt>RunnableFuture</tt> which when run will run the
     * underlying runnable and which, as a <tt>Future</tt>, will yield
     * the given value as its result and provide for cancellation of
     * the underlying task.
     * @since 1.6
     */
    protected <T> java.util.concurrent.RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new java.util.concurrent.FutureTask<T>(runnable, value);
    }

    /**
     * Returns a <tt>RunnableFuture</tt> for the given callable task.
     *
     * @param callable the callable task being wrapped
     * @return a <tt>RunnableFuture</tt> which when run will call the
     * underlying callable and which, as a <tt>Future</tt>, will yield
     * the callable's result as its result and provide for
     * cancellation of the underlying task.
     * @since 1.6
     */
    protected <T> java.util.concurrent.RunnableFuture<T> newTaskFor(java.util.concurrent.Callable<T> callable) {
        return new FutureTask<T>(callable);
    }

    public java.util.concurrent.Future<?> submit(Runnable task) {
        if (task == null) throw new NullPointerException();
        java.util.concurrent.RunnableFuture<Object> ftask = newTaskFor(task, null);
        execute(ftask);
        return ftask;
    }

    public <T> java.util.concurrent.Future<T> submit(Runnable task, T result) {
        if (task == null) throw new NullPointerException();
        java.util.concurrent.RunnableFuture<T> ftask = newTaskFor(task, result);
        execute(ftask);
        return ftask;
    }

    public <T> java.util.concurrent.Future<T> submit(java.util.concurrent.Callable<T> task) {
        if (task == null) throw new NullPointerException();
        java.util.concurrent.RunnableFuture<T> ftask = newTaskFor(task);
        execute(ftask);
        return ftask;
    }

    /**
     * the main mechanics of invokeAny.
     */
    private <T> T doInvokeAny(Collection<? extends java.util.concurrent.Callable<T>> tasks,
                            boolean timed, long nanos)
        throws InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
        if (tasks == null)
            throw new NullPointerException();
        int ntasks = tasks.size();
        if (ntasks == 0)
            throw new IllegalArgumentException();
        List<java.util.concurrent.Future<T>> futures= new ArrayList<java.util.concurrent.Future<T>>(ntasks);
        ExecutorCompletionService<T> ecs =
            new ExecutorCompletionService<T>(this);

        // For efficiency, especially in executors with limited
        // parallelism, check to see if previously submitted tasks are
        // done before submitting more of them. This interleaving
        // plus the exception mechanics account for messiness of main
        // loop.

        try {
            // Record exceptions so that if we fail to obtain any
            // result, we can throw the last exception we got.
            java.util.concurrent.ExecutionException ee = null;
            long lastTime = (timed)? System.nanoTime() : 0;
            Iterator<? extends java.util.concurrent.Callable<T>> it = tasks.iterator();

            // Start one task for sure; the rest incrementally
            futures.add(ecs.submit(it.next()));
            --ntasks;
            int active = 1;

            for (;;) {
                java.util.concurrent.Future<T> f = ecs.poll();
                if (f == null) {
                    if (ntasks > 0) {
                        --ntasks;
                        futures.add(ecs.submit(it.next()));
                        ++active;
                    }
                    else if (active == 0)
                        break;
                    else if (timed) {
                        f = ecs.poll(nanos, java.util.concurrent.TimeUnit.NANOSECONDS);
                        if (f == null)
                            throw new java.util.concurrent.TimeoutException();
                        long now = System.nanoTime();
                        nanos -= now - lastTime;
                        lastTime = now;
                    }
                    else
                        f = ecs.take();
                }
                if (f != null) {
                    --active;
                    try {
                        return f.get();
                    } catch (InterruptedException ie) {
                        throw ie;
                    } catch (java.util.concurrent.ExecutionException eex) {
                        ee = eex;
                    } catch (RuntimeException rex) {
                        ee = new java.util.concurrent.ExecutionException(rex);
                    }
                }
            }

            if (ee == null)
                ee = new java.util.concurrent.ExecutionException();
            throw ee;

        } finally {
            for (java.util.concurrent.Future<T> f : futures)
                f.cancel(true);
        }
    }

    public <T> T invokeAny(Collection<? extends java.util.concurrent.Callable<T>> tasks)
        throws InterruptedException, java.util.concurrent.ExecutionException {
        try {
            return doInvokeAny(tasks, false, 0);
        } catch (java.util.concurrent.TimeoutException cannotHappen) {
            assert false;
            return null;
        }
    }

    public <T> T invokeAny(Collection<? extends java.util.concurrent.Callable<T>> tasks,
                           long timeout, java.util.concurrent.TimeUnit unit)
        throws InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
        return doInvokeAny(tasks, true, unit.toNanos(timeout));
    }

    public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends java.util.concurrent.Callable<T>> tasks)
        throws InterruptedException {
        if (tasks == null)
            throw new NullPointerException();
        List<java.util.concurrent.Future<T>> futures = new ArrayList<java.util.concurrent.Future<T>>(tasks.size());
        boolean done = false;
        try {
            for (java.util.concurrent.Callable<T> t : tasks) {
                RunnableFuture<T> f = newTaskFor(t);
                futures.add(f);
                execute(f);
            }
            for (java.util.concurrent.Future<T> f : futures) {
                if (!f.isDone()) {
                    try {
                        f.get();
                    } catch (java.util.concurrent.CancellationException ignore) {
                    } catch (java.util.concurrent.ExecutionException ignore) {
                    }
                }
            }
            done = true;
            return futures;
        } finally {
            if (!done)
                for (java.util.concurrent.Future<T> f : futures)
                    f.cancel(true);
        }
    }

    public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends java.util.concurrent.Callable<T>> tasks,
                                                              long timeout, java.util.concurrent.TimeUnit unit)
        throws InterruptedException {
        if (tasks == null || unit == null)
            throw new NullPointerException();
        long nanos = unit.toNanos(timeout);
        List<java.util.concurrent.Future<T>> futures = new ArrayList<java.util.concurrent.Future<T>>(tasks.size());
        boolean done = false;
        try {
            for (Callable<T> t : tasks)
                futures.add(newTaskFor(t));

            long lastTime = System.nanoTime();

            // Interleave time checks and calls to execute in case
            // executor doesn't have any/much parallelism.
            Iterator<java.util.concurrent.Future<T>> it = futures.iterator();
            while (it.hasNext()) {
                execute((Runnable)(it.next()));
                long now = System.nanoTime();
                nanos -= now - lastTime;
                lastTime = now;
                if (nanos <= 0)
                    return futures;
            }

            for (java.util.concurrent.Future<T> f : futures) {
                if (!f.isDone()) {
                    if (nanos <= 0)
                        return futures;
                    try {
                        f.get(nanos, TimeUnit.NANOSECONDS);
                    } catch (CancellationException ignore) {
                    } catch (ExecutionException ignore) {
                    } catch (TimeoutException toe) {
                        return futures;
                    }
                    long now = System.nanoTime();
                    nanos -= now - lastTime;
                    lastTime = now;
                }
            }
            done = true;
            return futures;
        } finally {
            if (!done)
                for (Future<T> f : futures)
                    f.cancel(true);
        }
    }

}
