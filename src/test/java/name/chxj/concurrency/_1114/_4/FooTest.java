package name.chxj.concurrency._1114._4;

import lombok.SneakyThrows;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.junit.Assert.*;

/**
 * @author chenxiaojing
 */
public class FooTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void test() throws InterruptedException {
        Foo foo = new Foo();

        Thread A = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                foo.first(() -> System.out.print("one"));
            }
        });
        Thread B = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                foo.second(() -> System.out.print("two"));
            }
        });
        Thread C = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                foo.third(() -> System.out.print("three"));
            }
        });

        A.start();
        B.start();
        C.start();

        A.join();
        B.join();
        C.join();

        assertEquals("onetwothree", systemOutRule.getLog());
    }


}