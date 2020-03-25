package name.chxj.concurrency._1116._4;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.function.IntConsumer;

class ZeroEvenOdd {
    private int n;
    private volatile CyclicBarrier barrier0 = new CyclicBarrier(2);
    private volatile CyclicBarrier barrier1 = new CyclicBarrier(2);
    private volatile CyclicBarrier barrier2 = new CyclicBarrier(2);

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            printNumber.accept(0);
            try {
                if (i % 2 == 1) {
                    barrier1.await();
                    barrier0.await();
                } else {
                    barrier2.await();
                    barrier0.await();
                }
            } catch (BrokenBarrierException ignore) {
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i++) {
            if (i % 2 == 0) {
                try {
                    barrier2.await();
                } catch (BrokenBarrierException ignore) {
                }
                printNumber.accept(i);
                try {
                    barrier0.await();
                } catch (BrokenBarrierException ignore) {
                }
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                try {
                    barrier1.await();
                } catch (BrokenBarrierException ignore) {
                }
                printNumber.accept(i);
                try {
                    barrier0.await();
                } catch (BrokenBarrierException ignore) {
                }
            }
        }
    }
}