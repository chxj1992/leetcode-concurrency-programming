package name.chxj.concurrency._1115._4;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class FooBar {
    private int n;
    private volatile boolean fooTurn = true;
    private CyclicBarrier barrier = new CyclicBarrier(2);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            printFoo.run();
            fooTurn = false;
            try {
                barrier.await();
            } catch (BrokenBarrierException ignore) {}
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (fooTurn) {
            }
            printBar.run();
            fooTurn = true;
            try {
                barrier.await();
            } catch (BrokenBarrierException ignore) {}
        }
    }
}