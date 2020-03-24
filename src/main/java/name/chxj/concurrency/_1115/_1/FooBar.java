package name.chxj.concurrency._1115._1;

class FooBar {
    private int n;
    private volatile boolean fooTurn = true;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (this) {
                if (!fooTurn) {
                    this.wait();
                }
                printFoo.run();
                fooTurn = false;
                this.notify();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (this) {
                if (fooTurn) {
                    this.wait();
                }
                printBar.run();
                fooTurn = true;
                this.notify();
            }
        }
    }
}