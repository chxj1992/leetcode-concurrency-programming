package name.chxj.concurrency._1115._4;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class FooBar {
    private int n;
    private CyclicBarrier barrier1 = new CyclicBarrier(2);
    private CyclicBarrier barrier2 = new CyclicBarrier(2);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            try {
                barrier2.await();
            } catch (BrokenBarrierException ignore) {}
            printFoo.run();
            try {
                barrier1.await();
            } catch (BrokenBarrierException ignore) {}
        }
        try {
            barrier2.await();
        } catch (BrokenBarrierException ignore) {}
    }

    public void bar(Runnable printBar) throws InterruptedException {
        try {
            barrier2.await();
        } catch (BrokenBarrierException ignore) {}
        for (int i = 0; i < n; i++) {
            try {
                barrier1.await();
            } catch (BrokenBarrierException ignore) {}
            printBar.run();
            try {
                barrier2.await();
            } catch (BrokenBarrierException ignore) {}
        }
    }
}