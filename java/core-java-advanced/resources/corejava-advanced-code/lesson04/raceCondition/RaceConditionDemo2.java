package raceCondition;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RaceConditionDemo2 {
    public static LinkedList<Integer> list = new LinkedList<>();
   
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 1; i <= 100; i++) {
            int taskId = i;
            executor.execute(() -> {
                for (int k = 1; k <= 1000; k++)
                    list.add(taskId * 1000 + k);
                System.out.println(taskId + ": " + list.size());
            });
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("Final length: " + list.size());
        System.out.println(list);
    }
}