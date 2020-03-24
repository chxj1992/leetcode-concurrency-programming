package name.chxj.concurrency._1114._2;

class Foo {

    private volatile int flag = 1;

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        flag = 2;
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (flag != 2) {
            Thread.yield();
        }
        printSecond.run();
        flag = 3;
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (flag != 3) {
            Thread.yield();
        }
        printThird.run();
    }
}