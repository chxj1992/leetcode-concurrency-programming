package name.chxj.concurrency._1114._1;

class Foo {

    private volatile int flag = 1;

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (flag == 1) {
                    printFirst.run();
                    flag = 2;
                    this.notifyAll();
                    break;
                }
            }
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (flag == 2) {
                    printSecond.run();
                    flag = 3;
                    this.notifyAll();
                    break;
                } else {
                    this.wait();
                }
            }
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (flag == 3) {
                    printThird.run();
                    break;
                } else {
                    this.wait();
                }
            }
        }
    }
}