package locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class QueueDemo {
    public static Queue queue = new Queue();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 1; i <= 100; i++) {
            int taskId = i;
            executor.execute(() -> {
                for (int k = 1; k <= 1000; k++) 
                    queue.add(taskId * 1000 + k);
            });
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        int size = 0;
        while (queue.remove() != null) size++;
        System.out.println("Final size: " + size);
    }
}