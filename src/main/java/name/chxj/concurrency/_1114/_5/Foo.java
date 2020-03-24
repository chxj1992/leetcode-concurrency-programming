package name.chxj.concurrency._1114._5;

import java.util.concurrent.Semaphore;

class Foo {

    private Semaphore semaphore2 = new Semaphore(0);
    private Semaphore semaphore3 = new Semaphore(0);

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        semaphore2.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        semaphore2.acquire();
        printSecond.run();
        semaphore3.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        semaphore3.acquire();
        printThird.run();
    }
}