package name.chxj.concurrency._1226._1;

import java.util.concurrent.Semaphore;

class DiningPhilosophers {

    private volatile Semaphore[] forks = new Semaphore[]{
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1),
    };

    public DiningPhilosophers() {

    }

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {

        int leftForkNo = philosopher;
        int rightForkNo = (philosopher + 1) % 5;

        if (philosopher % 2 == 0) {
            forks[leftForkNo].acquire();
            pickLeftFork.run();

            forks[rightForkNo].acquire();
            pickRightFork.run();
        } else {
            forks[rightForkNo].acquire();
            pickRightFork.run();

            forks[leftForkNo].acquire();
            pickLeftFork.run();
        }

        eat.run();

        putLeftFork.run();
        forks[leftForkNo].release();

        putRightFork.run();
        forks[rightForkNo].release();
    }

}