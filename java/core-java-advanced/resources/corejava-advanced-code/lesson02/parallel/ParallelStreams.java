package parallel;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.*;

public class ParallelStreams {
	public static void title(String heading) {
		System.out.printf("%n=== %s ===%n%n", heading);
	}
	
	public static double time(Runnable task) {
		long start = System.nanoTime();
		task.run();
		long end = System.nanoTime();
		return (end - start) * 1E-9;
	}
	
    public static void main(String[] args) throws IOException {
        String contents = new String(Files.readAllBytes(
                Paths.get("../alice.txt")), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split("\\PL+"));

        title("Bad code for short words");
        // Very bad code ahead
        int[] shortWords = new int[10];
        words.parallelStream().forEach(
            s -> { if (s.length() < 10) shortWords[s.length()]++; });
        System.out.println(Arrays.toString(shortWords));

        // Try again--the result will likely be different (and also wrong)
        Arrays.fill(shortWords, 0);
        words.parallelStream().forEach(
            s -> { if (s.length() < 10) shortWords[s.length()]++; });
        System.out.println(Arrays.toString(shortWords));

        title("Good code for short words");
        // Remedy: Group and count 
        Map<Integer, Long> shortWordCounts =
            words.parallelStream()
                .filter(s -> s.length() < 10)
                .collect(
                    groupingBy(
                        String::length,
                        counting()));
               
        System.out.println(shortWordCounts);
        
        title("Without parallel");
        long start = 1_000_000_000L;
        long end = 1_005_000_000L;
        System.out.println("Seconds: " + time(() -> 
        	System.out.println("Primes: " +  
        			LongStream.range(start, end)
        				.filter(n -> BigInteger.valueOf(n).isProbablePrime(100))
        				.count())));
        
        title("With parallel");
        System.out.println("Seconds: " + time(() -> 
        	System.out.println("Primes: " +
        			LongStream.range(start, end)
        				.parallel()
        				.filter(n -> BigInteger.valueOf(n).isProbablePrime(100))
        				.count())));
   }
}