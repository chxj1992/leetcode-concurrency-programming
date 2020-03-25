package name.chxj.concurrency._1116._3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

class ZeroEvenOdd {
    private int n;
    private volatile ReentrantLock lock = new ReentrantLock();
    private volatile boolean zeroFlag = true;
    private volatile boolean oddFlag = true;
    private volatile Condition zeroCondition = lock.newCondition();
    private volatile Condition oddCondition = lock.newCondition();
    private volatile Condition evenCondition = lock.newCondition();

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            lock.lock();
            try {
                while (!zeroFlag) {
                    zeroCondition.await();
                }
                printNumber.accept(0);
                zeroFlag = false;
                if (i % 2 == 1) {
                    oddCondition.signal();
                } else {
                    evenCondition.signal();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i++) {
            if (i % 2 == 0) {
                lock.lock();
                try {
                    while (zeroFlag || oddFlag) {
                        evenCondition.await();
                    }
                    printNumber.accept(i);
                    zeroFlag = true;
                    oddFlag = !oddFlag;
                    zeroCondition.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                lock.lock();
                try {
                    while (zeroFlag || !oddFlag) {
                        oddCondition.await();
                        Thread.yield();
                    }
                    printNumber.accept(i);
                    zeroFlag = true;
                    oddFlag = !oddFlag;
                    zeroCondition.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}