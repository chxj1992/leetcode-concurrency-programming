package name.chxj.concurrency._1116._3;

import lombok.SneakyThrows;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.junit.Assert.assertEquals;

/**
 * @author chenxiaojing
 */
public class ZeroEvenOddTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void test() throws InterruptedException {
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(5);

        Thread zero = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                zeroEvenOdd.zero(System.out::print);
            }
        });
        Thread even = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                zeroEvenOdd.even(System.out::print);
            }
        });

        Thread odd = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                zeroEvenOdd.odd(System.out::print);
            }
        });

        zero.start();
        even.start();
        odd.start();

        zero.join();
        even.join();
        odd.join();

        assertEquals("0102030405", systemOutRule.getLog());
    }
}

