package utils;

import java.util.List;

/**
 * Consumer that takes items from a bounded queue and adds them to a destination list.
 * Stops when it receives a null "poison pill" from the producer.
 */
public class Consumer<T> implements Runnable {
    private final BoundedBlockingQueue<T> queue;
    private final List<T> destination;
    private final String name;
    private int itemsConsumed = 0;

    /**
     * Creates a new Consumer.
     * 
     * @param queue the bounded blocking queue to consume items from
     * @param destination the destination list to add consumed items to
     */
    public Consumer(BoundedBlockingQueue<T> queue, List<T> destination) {
        this(queue, destination, "Consumer");
    }

    /**
     * Creates a new Consumer with a custom name.
     * 
     * @param queue the bounded blocking queue to consume items from
     * @param destination the destination list to add consumed items to
     * @param name the name for this consumer (for logging)
     */
    public Consumer(BoundedBlockingQueue<T> queue, List<T> destination, String name) {
        this.queue = queue;
        this.destination = destination;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (true) {
                T value = queue.take();
                
                // Check for poison pill (null signals end of data)
                if (value == null) {
                    System.out.println(name + " received poison pill - stopping");
                    break;
                }
                
                destination.add(value);
                itemsConsumed++;
                System.out.println(name + " consumed: " + value + 
                                 " (Queue size: " + queue.size() + "/" + queue.getCapacity() + 
                                 ", Total consumed: " + itemsConsumed + ")");
                
                // Optional: Add small delay to better observe threading behavior
                Thread.sleep(15);
            }
            System.out.println(name + " finished - total items consumed: " + itemsConsumed);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(name + " was interrupted");
        }
    }

    /**
     * Gets the name of this consumer.
     * 
     * @return the consumer name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the number of items consumed by this consumer.
     * 
     * @return the number of items consumed
     */
    public int getItemsConsumed() {
        return itemsConsumed;
    }
}