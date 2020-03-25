package name.chxj.concurrency._1195._1;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

class FizzBuzz {

    private volatile int n;
    private volatile Semaphore fizzSemaphore = new Semaphore(0);
    private volatile Semaphore buzzSemaphore = new Semaphore(0);
    private volatile Semaphore fizzbuzzSemaphore = new Semaphore(0);
    private volatile Semaphore numberSemaphore = new Semaphore(1);

    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 != 0) {
                fizzSemaphore.acquire();
                printFizz.run();
                trigger(i + 1);
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 3 != 0 && i % 5 == 0) {
                buzzSemaphore.acquire();
                printBuzz.run();
                trigger(i + 1);
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                fizzbuzzSemaphore.acquire();
                printFizzBuzz.run();
                trigger(i + 1);
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 3 != 0 && i % 5 != 0) {
                numberSemaphore.acquire();
                printNumber.accept(i);
                trigger(i + 1);
            }
        }
    }

    private void trigger(int i) {
        if (i > n) {
            return;
        }
        if (i % 3 != 0 && i % 5 != 0) {
            numberSemaphore.release();
        } else if (i % 3 == 0 && i % 5 != 0) {
            fizzSemaphore.release();
        } else if (i % 3 != 0) {
            buzzSemaphore.release();
        } else {
            fizzbuzzSemaphore.release();
        }
    }
}