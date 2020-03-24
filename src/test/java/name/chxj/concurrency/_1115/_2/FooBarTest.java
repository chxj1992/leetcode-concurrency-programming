package name.chxj.concurrency._1115._2;

import lombok.SneakyThrows;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.junit.Assert.assertEquals;

/**
 * @author chenxiaojing
 */
public class FooBarTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void test() throws InterruptedException {
        FooBar fooBar = new FooBar(10);

        Thread t1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                fooBar.foo(() -> System.out.print("foo"));
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                fooBar.bar(() -> System.out.print("bar"));
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        assertEquals("foobarfoobarfoobarfoobarfoobarfoobarfoobarfoobarfoobarfoobar", systemOutRule.getLog());
    }
}

