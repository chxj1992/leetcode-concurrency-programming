package name.chxj.concurrency._1114._3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Foo {

    private volatile int flag = 1;
    private volatile ReentrantLock lock = new ReentrantLock();
    private volatile Condition condition2 = lock.newCondition();
    private volatile Condition condition3 = lock.newCondition();

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        lock.lock();
        try {
            printFirst.run();
            flag = 2;
            condition2.signal();
        } finally {
            lock.unlock();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        lock.lock();
        try {
            if (flag != 2) {
                condition2.await();
            }
            printSecond.run();
            flag = 3;
            condition3.signal();
        } finally {
            lock.unlock();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        lock.lock();
        try {
            if (flag != 3) {
                condition3.await();
            }
            printThird.run();
        } finally {
            lock.unlock();
        }
    }
}