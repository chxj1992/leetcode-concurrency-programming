package name.chxj.concurrency._1117._3;

import lombok.SneakyThrows;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;

/**
 * @author chenxiaojing
 */
public class H2OTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void test() throws InterruptedException {
        H2O h2O = new H2O();

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Thread h1 = new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    for (int i = 0; i < 5; i++) {
                        h2O.hydrogen(() -> System.out.print("H"));
                    }
                }
            });
            Thread h2 = new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    for (int i = 0; i < 5; i++) {
                        h2O.hydrogen(() -> System.out.print("H"));
                    }
                }
            });

            Thread o = new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    for (int i = 0; i < 5; i++) {
                        h2O.oxygen(() -> System.out.print("O"));
                    }
                }
            });
            h1.start();
            h2.start();
            o.start();
            threads.add(h1);
            threads.add(h2);
            threads.add(o);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        String res = systemOutRule.getLog();

        List<String> group = new ArrayList<>();
        for (int i = 0; i < res.length(); i++) {
            group.add(String.valueOf(res.charAt(i)));
            if (group.size() == 3) {
                group.sort(String::compareTo);
                if (!"HHO".equals(String.join("", group))) {
                    fail(res + " is invalid");
                }
                group.clear();
            }
        }
    }

}