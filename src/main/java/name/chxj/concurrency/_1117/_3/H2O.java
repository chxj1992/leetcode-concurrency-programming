package name.chxj.concurrency._1117._3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class H2O {

    private volatile ReentrantLock lock = new ReentrantLock();
    private volatile Condition hCondition = lock.newCondition();
    private volatile Condition oCondition = lock.newCondition();
    private volatile int hCount = 0;
    private volatile int oCount = 0;

    public H2O() {
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        lock.lock();
        try {
            while (hCount >= 2) {
                hCondition.await();
            }
            releaseHydrogen.run();
            hCount += 1;
            if (hCount == 2) {
                oCount = 0;
                oCondition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        lock.lock();
        try {
            while (oCount >= 1) {
                oCondition.await();
            }
            releaseOxygen.run();
            oCount += 1;
            hCount = 0;
            hCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}