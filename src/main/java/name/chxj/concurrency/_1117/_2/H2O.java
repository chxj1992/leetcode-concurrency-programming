package name.chxj.concurrency._1117._2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

class H2O {

    private Semaphore h = new Semaphore(2);
    private Semaphore o = new Semaphore(1);

    private CyclicBarrier barrier = new CyclicBarrier(3, () -> {
        h.release(2);
        o.release();
    });

    public H2O() {
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        h.acquire();
        releaseHydrogen.run();
        try {
            barrier.await();
        } catch (BrokenBarrierException ignore) {
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        o.acquire();
        releaseOxygen.run();
        try {
            barrier.await();
        } catch (BrokenBarrierException ignore) {
        }
    }
}