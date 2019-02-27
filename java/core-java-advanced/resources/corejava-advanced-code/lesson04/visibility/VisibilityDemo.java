package visibility;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class VisibilityDemo {
    private static volatile boolean done = false; 
    
    public static void main(String[] args) {
        Runnable hellos = () -> {
            for (int i = 1; i <= 1000; i++) 
                System.out.println("Hello " + i);
            done = true;
        };
        Runnable goodbye = () -> {
            int i = 1;
            while (!done) i++; 
            System.out.println("Goodbye " + i);
        };
        Executor executor = Executors.newCachedThreadPool();
        executor.execute(hellos);        
        executor.execute(goodbye);        
    }
}
