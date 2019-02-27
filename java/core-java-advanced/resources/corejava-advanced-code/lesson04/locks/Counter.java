package locks;

public class Counter {
    private int value;
    public synchronized int increment() {
        value++;
        return value;
    }
}