package blockingQueue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class BlockingQueueTest {
   private static final int FILE_QUEUE_SIZE = 10;
   private static final int SEARCH_THREADS = 100;
   private static final Path DUMMY = Paths.get("DUMMY");
   private static BlockingQueue<Path> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

   public static void main(String[] args) {
	   Path directory = Paths.get("..");
	   String keyword = "static";
       Runnable enumerator = () -> {
    	   try {
               enumerate(directory);
               queue.put(DUMMY);
    	   } catch (IOException e) {
    		   e.printStackTrace();
    	   } catch (InterruptedException e) {
    	   }            
       };
       ExecutorService executor = Executors.newFixedThreadPool(SEARCH_THREADS + 1);
       executor.execute(enumerator);
       for (int i = 1; i <= SEARCH_THREADS; i++) {
            Runnable searcher = () -> {
               try {
                  boolean done = false;
                  while (!done) {
                     Path file = queue.take();
                     if (file == DUMMY) {
                        queue.put(file);
                        done = true;
                     }
                     else search(file, keyword);
                  }
               }
               catch (IOException e) {
                  e.printStackTrace();
               }
               catch (InterruptedException e) {
               }               
            };
            executor.execute(searcher);
       }
   }
   
   
   /**
    * Recursively enumerates all files in a given directory and its subdirectories.
    * @param directory the directory in which to start
 * @throws IOException 
    */
   public static void enumerate(Path directory) throws InterruptedException, IOException
   {
	   try (Stream<Path> entries = Files.walk(directory)) {
           entries.filter(f -> f.toString().endsWith(".java")).forEach(p -> {
        	   try {
        		   queue.put(p);
        	   } catch (InterruptedException ex) {
        	   }
           });
       }
   }
   
   /**
    * Searches a file for a given keyword and prints all matching lines.
    * @param file the file to search
    * @param keyword the keyword to search for
    */
   public static void search(Path file, String keyword) throws IOException {
      try (Scanner in = new Scanner(file, "UTF-8")) {
    	  in.useDelimiter("\\PL+");
          int lineNumber = 0;
          while (in.hasNextLine()) {
        	  lineNumber++;
        	  String line = in.nextLine();
        	  if (line.contains(keyword)) 
        		  System.out.printf("%s:%d:%s%n", file, lineNumber, line);
          }
      }
   }
}
