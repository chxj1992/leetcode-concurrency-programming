package name.chxj.concurrency._1115._3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class FooBar {
    private int n;
    private volatile boolean fooTurn = true;
    private ReentrantLock lock = new ReentrantLock();
    private Condition fooWait = lock.newCondition();
    private Condition barWait = lock.newCondition();

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                if (!fooTurn) {
                    fooWait.await();
                }
                printFoo.run();
                fooTurn = false;
                barWait.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                if (fooTurn) {
                    barWait.await();
                }
                printBar.run();
                fooTurn = true;
                fooWait.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}