package standardAnnotations;

import java.lang.reflect.Array;

public class StandardAnnotations {
    public class Point {
        // @Override public boolean equals(Point other) { return false; }        
    }
    
    public static <T> T[] nCopies(int n, T value) {
        Class<?> cl = value.getClass();
        // @SuppressWarnings("unchecked") 
        T[] result = (T[]) Array.newInstance(cl,  n);
        for (int i = 0; i < n; i++) result[i] = value;
        return result;
    }
        
    @FunctionalInterface
    public interface IntFunction<R> {
        R apply(int value);
        // void consume(R value);
    }
    
    @Deprecated public static void show(Object obj) { System.out.println(obj); }
    
    public static void main(String[] args) {
    	show(args);
    }
}
