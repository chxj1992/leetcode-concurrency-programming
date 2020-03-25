package name.chxj.concurrency._1195._1;

import lombok.SneakyThrows;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.junit.Assert.*;

/**
 * @author chenxiaojing
 */
public class FizzBuzzTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void test() throws InterruptedException {
        FizzBuzz foo = new FizzBuzz(15);

        Thread A = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                foo.fizz(() -> System.out.print("fizz"));
            }
        });
        Thread B = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                foo.buzz(() -> System.out.print("buzz"));
            }
        });
        Thread C = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                foo.fizzbuzz(() -> System.out.print("fizzbuzz"));
            }
        });

        Thread D = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                foo.number(System.out::print);
            }
        });

        A.start();
        B.start();
        C.start();
        D.start();

        A.join();
        B.join();
        C.join();
        D.join();

        assertEquals("12fizz4buzzfizz78fizzbuzz11fizz1314fizzbuzz", systemOutRule.getLog());
    }

}