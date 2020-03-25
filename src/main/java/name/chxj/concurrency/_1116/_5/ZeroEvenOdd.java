package name.chxj.concurrency._1116._5;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

class ZeroEvenOdd {
    private int n;
    private volatile Semaphore semaphore0 = new Semaphore(1);
    private volatile Semaphore semaphore1 = new Semaphore(0);
    private volatile Semaphore semaphore2 = new Semaphore(0);

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            semaphore0.acquire();
            printNumber.accept(0);
            if (i % 2 == 1) {
                semaphore1.release();
            } else {
                semaphore2.release();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i++) {
            if (i % 2 == 0) {
                semaphore2.acquire();
                printNumber.accept(i);
                semaphore0.release();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                semaphore1.acquire();
                printNumber.accept(i);
                semaphore0.release();
            }
        }
    }
}