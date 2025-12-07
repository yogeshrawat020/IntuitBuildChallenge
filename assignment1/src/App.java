import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import utils.BoundedBlockingQueue;
import utils.Consumer;
import utils.Producer;

/**
 * Main application demonstrating the Producer-Consumer pattern
 * using a bounded blocking queue with wait/notify synchronization.
 */
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Producer-Consumer Pattern Demo ===");
        System.out.println("Using BoundedBlockingQueue with wait/notify mechanism\n");

        // Source data to be transferred
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Thread-safe destination list
        List<Integer> destination = Collections.synchronizedList(new ArrayList<>());

        // Create bounded queue with capacity of 3
        BoundedBlockingQueue<Integer> queue = new BoundedBlockingQueue<>(3);

        System.out.println("Initial Configuration:");
        System.out.println("  Source items: " + source.size());
        System.out.println("  Queue capacity: " + queue.getCapacity());
        System.out.println("  Starting threads...\n");

        // Create producer and consumer threads
        Thread producerThread = new Thread(
            new Producer<>(queue, source, "Producer-1"), 
            "Producer-Thread"
        );
        
        Thread consumerThread = new Thread(
            new Consumer<>(queue, destination, "Consumer-1"), 
            "Consumer-Thread"
        );

        // Start both threads
        long startTime = System.currentTimeMillis();
        producerThread.start();
        consumerThread.start();

        // Wait for both threads to complete
        producerThread.join();
        consumerThread.join();
        long endTime = System.currentTimeMillis();

        // Display results
        System.out.println("\n=== Results ===");
        System.out.println("Source:      " + source);
        System.out.println("Destination: " + destination);
        System.out.println("\nVerification:");
        System.out.println("  Items transferred: " + destination.size());
        System.out.println("  All items match: " + source.equals(destination));
        System.out.println("  Execution time: " + (endTime - startTime) + "ms");
        System.out.println("  Queue final size: " + queue.size());

        if (source.equals(destination)) {
            System.out.println("\n✓ SUCCESS: All items transferred correctly!");
        } else {
            System.out.println("\n✗ ERROR: Items don't match!");
        }
    }
}