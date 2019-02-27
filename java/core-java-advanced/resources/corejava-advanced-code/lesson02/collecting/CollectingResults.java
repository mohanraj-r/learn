package collecting;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.*;

public class CollectingResults {
	public static void title(String heading) {
		System.out.printf("%n=== %s ===%n%n", heading);
	}
	
    public static Stream<String> words(String filename) throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get(filename)),
    		StandardCharsets.UTF_8);
        return Pattern.compile("\\PL+").splitAsStream(contents);
    }

    public static <T> void show(String label, Collection<T> coll) {
        System.out.print(label + ": " + coll.getClass().getName());
        System.out.println("["
            + coll.stream()
            	.limit(10)
            	.map(Object::toString)
            	.collect(Collectors.joining(", "))
            + "]");
    }

    public static void main(String[] args) throws IOException {
    	title("iterator");
        Iterator<Integer> iter = Stream.iterate(0, n -> n + 1).limit(10).iterator();
        while (iter.hasNext()) System.out.print(iter.next() + " ");
        System.out.println();
        
        title("toArray");
        Object[] numbers = Stream.iterate(0, n -> n + 1)
           .limit(10)
           .toArray();
        System.out.println(numbers); // Note it's an Object[] array
        System.out.println(Arrays.toString(numbers));

        Integer[] numbers2 = Stream.iterate(0, n -> n + 1)
           .limit(10)
           .toArray(Integer[]::new);
        System.out.println(numbers2); // Note it's an Integer[] array
        System.out.println(Arrays.toString(numbers2));

        title("collect");
        show("List", words("../alice.txt").collect(Collectors.toList()));
        show("Set", words("../alice.txt").collect(Collectors.toSet()));

        TreeSet<String> treeSet = words("../alice.txt").collect(
                Collectors.toCollection(TreeSet::new));
        show("TreeSet", treeSet);        

        title("summarizingInt");
        IntSummaryStatistics summary = words("../alice.txt").collect(
                Collectors.summarizingInt(String::length));
        double averageWordLength = summary.getAverage();
        double maxWordLength = summary.getMax();
        System.out.println("Average word length: " + averageWordLength);
        System.out.println("Max word length: " + maxWordLength);

        title("forEach");
        words("../alice.txt").limit(10).forEach(System.out::println);
    }
}
