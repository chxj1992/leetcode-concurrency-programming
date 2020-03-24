package name.chxj.concurrency._1116._2;

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
            while (!zeroFlag) {
                Thread.yield();
            }
            printNumber.accept(0);
            zeroFlag = false;
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i++) {
            if (i % 2 == 0) {
                while (zeroFlag || oddFlag) {
                    Thread.yield();
                }
                printNumber.accept(i);
                zeroFlag = true;
                oddFlag = !oddFlag;
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                while (zeroFlag || !oddFlag) {
                    Thread.yield();
                }
                printNumber.accept(i);
                zeroFlag = true;
                oddFlag = !oddFlag;
            }
        }
    }
}