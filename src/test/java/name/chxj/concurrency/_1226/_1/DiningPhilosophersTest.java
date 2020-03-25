package name.chxj.concurrency._1226._1;

import lombok.SneakyThrows;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static junit.framework.TestCase.fail;

/**
 * @author chenxiaojing
 */
public class DiningPhilosophersTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void test() throws InterruptedException {
        DiningPhilosophers diningPhilosophers = new DiningPhilosophers();

        List<int[]> output = new CopyOnWriteArrayList<>();
        List<Thread> philosophers = new ArrayList<>();

        int peopleNum = 5;
        int eatTime = 1;

        for (int i = 0; i < peopleNum; i++) {
            int philosopherNum = i;
            Thread philosopher = new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    for (int j = 0; j < eatTime; j++) {
                        diningPhilosophers.wantsToEat(
                                philosopherNum,
                                () -> output.add(new int[]{philosopherNum, 1, 1}),
                                () -> output.add(new int[]{philosopherNum, 2, 1}),
                                () -> output.add(new int[]{philosopherNum, 0, 3}),
                                () -> output.add(new int[]{philosopherNum, 1, 2}),
                                () -> output.add(new int[]{philosopherNum, 2, 2})
                        );
                    }
                }
            });
            philosophers.add(philosopher);
            philosopher.start();
        }

        for (Thread philosopher : philosophers) {
            philosopher.join();
        }

        HashMap<Integer, Boolean> forkMap = new HashMap<>();

        for (int[] action : output) {
            int id = action[0];
            int side = action[1];
            int move = action[2];

            // pick up
            if (move == 1) {
                int forkNo = id + side;
                if (forkNo == peopleNum + 1) {
                    forkNo = 1;
                }
                Boolean occupied = forkMap.getOrDefault(forkNo, false);
                if (occupied) {
                    fail("the " + forkNo + "th fork is occupied !");
                }
                forkMap.put(forkNo, true);
            }

            // put down
            if (move == 2) {
                int forkNo = id + side;
                if (forkNo == peopleNum + 1) {
                    forkNo = 1;
                }
                Boolean occupied = forkMap.getOrDefault(forkNo, false);
                if (!occupied) {
                    fail("the " + forkNo + "th fork is not yours !");
                }
                forkMap.put(forkNo, false);
            }
        }
    }

}