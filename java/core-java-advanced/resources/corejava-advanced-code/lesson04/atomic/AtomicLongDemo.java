package atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongDemo {
    public static AtomicLong count = new AtomicLong();

    public static void main(String[] args) throws InterruptedException {
        long start = System.nanoTime();
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 1; i <= 1000; i++) {
            Runnable task = () -> {
                for (int k = 1; k <= 100000; k++)
                    count.incrementAndGet();
            };
            executor.execute(task);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("Final value: " + count);
        long end = System.nanoTime();
        System.out.println((end - start) * 1E-9 + " seconds");
    }
}
