package name.chxj.concurrency._1115._2;

class FooBar {
    private int n;
    private volatile boolean fooTurn = true;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (!fooTurn) {
                Thread.yield();
            }
            printFoo.run();
            fooTurn = false;
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (fooTurn) {
                Thread.yield();
            }
            printBar.run();
            fooTurn = true;
        }
    }
}