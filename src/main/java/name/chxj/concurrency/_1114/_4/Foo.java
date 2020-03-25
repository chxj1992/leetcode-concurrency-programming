package name.chxj.concurrency._1114._4;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Foo {
    private volatile CyclicBarrier barrier2 = new CyclicBarrier(2);
    private volatile CyclicBarrier barrier3 = new CyclicBarrier(2);

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        try {
            barrier2.await();
        } catch (BrokenBarrierException ignore) {
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        try {
            barrier2.await();
        } catch (BrokenBarrierException ignore) {
        }
        printSecond.run();
        try {
            barrier3.await();
        } catch (BrokenBarrierException ignore) {
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        try {
            barrier3.await();
        } catch (BrokenBarrierException ignore) {
        }
        printThird.run();
    }
}