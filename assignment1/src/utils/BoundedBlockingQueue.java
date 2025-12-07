
package utils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A bounded blocking queue implementation using wait/notify mechanism.
 * This queue blocks when:
 * - put() is called and the queue is at capacity
 * - take() is called and the queue is empty
 * 
 * Thread-safe implementation using synchronized methods and wait/notify.
 */
public class BoundedBlockingQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    /**
     * Creates a bounded blocking queue with the specified capacity.
     * 
     * @param capacity the maximum number of elements the queue can hold
     * @throws IllegalArgumentException if capacity <= 0
     */
    public BoundedBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.capacity = capacity;
    }

    /**
     * Inserts the specified element into this queue, waiting if necessary
     * for space to become available.
     * 
     * @param item the element to add
     * @throws InterruptedException if interrupted while waiting
     */
    public synchronized void put(T item) throws InterruptedException {
        while (queue.size() == capacity) {
            // Queue is full, wait for space
            wait();
        }
        queue.add(item);
        // Notify waiting consumers that an element is available
        notifyAll();
    }

    /**
     * Retrieves and removes the head of this queue, waiting if necessary
     * until an element becomes available.
     * 
     * @return the head of this queue
     * @throws InterruptedException if interrupted while waiting
     */
    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty()) {
            // Queue is empty, wait for elements
            wait();
        }
        T item = queue.poll();
        // Notify waiting producers that space is available
        notifyAll();
        return item;
    }

    /**
     * Returns the number of elements currently in this queue.
     * 
     * @return the number of elements in this queue
     */
    public synchronized int size() {
        return queue.size();
    }

    /**
     * Returns true if this queue contains no elements.
     * 
     * @return true if this queue is empty
     */
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Returns the maximum capacity of this queue.
     * 
     * @return the capacity of this queue
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns the number of additional elements that this queue can
     * accept without blocking.
     * 
     * @return the remaining capacity
     */
    public synchronized int remainingCapacity() {
        return capacity - queue.size();
    }
}