package name.chxj.concurrency._1116._1;

import java.util.function.IntConsumer;

class ZeroEvenOdd {
    private int n;
    private volatile boolean zeroFlag = true;
    private volatile boolean oddFlag = true;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            synchronized (this) {
                while (!zeroFlag) {
                    this.wait();
                }
                printNumber.accept(0);
                zeroFlag = false;
                this.notifyAll();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i++) {
            if (i % 2 == 0) {
                synchronized (this) {
                    while (zeroFlag || oddFlag) {
                        this.wait();
                    }
                    printNumber.accept(i);
                    zeroFlag = true;
                    oddFlag = !oddFlag;
                    this.notifyAll();
                }
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                synchronized (this) {
                    while (zeroFlag || !oddFlag) {
                        this.wait();
                    }
                    printNumber.accept(i);
                    zeroFlag = true;
                    oddFlag = !oddFlag;
                    this.notifyAll();
                }
            }
        }
    }
}