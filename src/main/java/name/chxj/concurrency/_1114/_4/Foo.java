package name.chxj.concurrency._1114._4;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Foo {
    private volatile int flag = 1;
    private volatile CyclicBarrier barrier = new CyclicBarrier(1);

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        flag = 2;
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (flag != 2) {
            try {
                barrier.await();
            } catch (BrokenBarrierException ignore) {
            }
        }
        printSecond.run();
        flag = 3;
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (flag != 3) {
            try {
                barrier.await();
            } catch (BrokenBarrierException ignore) {
            }
        }
        printThird.run();
    }
}