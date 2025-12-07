package utils;

import java.util.List;

/**
 * Producer that reads items from a source list and puts them into a bounded queue.
 * Uses a null "poison pill" to signal completion to consumers.
 */
public class Producer<T> implements Runnable {
    private final BoundedBlockingQueue<T> queue;
    private final List<T> source;
    private final String name;

    /**
     * Creates a new Producer.
     * 
     * @param queue the bounded blocking queue to produce items into
     * @param source the source list to read items from
     */
    public Producer(BoundedBlockingQueue<T> queue, List<T> source) {
        this(queue, source, "Producer");
    }

    /**
     * Creates a new Producer with a custom name.
     * 
     * @param queue the bounded blocking queue to produce items into
     * @param source the source list to read items from
     * @param name the name for this producer (for logging)
     */
    public Producer(BoundedBlockingQueue<T> queue, List<T> source, String name) {
        this.queue = queue;
        this.source = source;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            for (T value : source) {
                queue.put(value);
                System.out.println(name + " produced: " + value + 
                                 " (Queue size: " + queue.size() + "/" + queue.getCapacity() + ")");
                
                // Optional: Add small delay to better observe threading behavior
                Thread.sleep(10);
            }
            // Send poison pill to signal completion
            queue.put(null);
            System.out.println(name + " finished - sent poison pill");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(name + " was interrupted");
        }
    }

    /**
     * Gets the name of this producer.
     * 
     * @return the producer name
     */
    public String getName() {
        return name;
    }
}