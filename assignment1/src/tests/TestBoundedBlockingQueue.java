package tests;

import utils.BoundedBlockingQueue;
import java.util.ArrayList;
import java.util.List;

public class TestBoundedBlockingQueue {
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("=== Testing BoundedBlockingQueue ===\n");
        
        testBasicPutTake();
        testCapacityLimit();
        testFIFOOrder();
        testBlockingWhenFull();
        testBlockingWhenEmpty();
        testMultipleThreads();
        testNullElements();
        
        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + testsPassed);
        System.out.println("Failed: " + testsFailed);
        
        if (testsFailed == 0) {
            System.out.println("\n✓ ALL TESTS PASSED!");
        } else {
            System.out.println("\n✗ SOME TESTS FAILED!");
        }
    }

    static void testBasicPutTake() {
        try {
            BoundedBlockingQueue<Integer> queue = new BoundedBlockingQueue<>(5);
            
            queue.put(1);
            queue.put(2);
            queue.put(3);
            
            assertEquals("Size should be 3", 3, queue.size());
            assertEquals("Take should return 1", 1, queue.take());
            assertEquals("Take should return 2", 2, queue.take());
            assertEquals("Take should return 3", 3, queue.take());
            assertEquals("Queue should be empty", 0, queue.size());
            
            pass("testBasicPutTake");
        } catch (Exception e) {
            fail("testBasicPutTake", e);
        }
    }

    static void testCapacityLimit() {
        try {
            BoundedBlockingQueue<Integer> queue = new BoundedBlockingQueue<>(3);
            
            queue.put(1);
            queue.put(2);
            queue.put(3);
            
            assertEquals("Size should be 3", 3, queue.size());
            assertEquals("Remaining capacity should be 0", 0, queue.remainingCapacity());
            
            pass("testCapacityLimit");
        } catch (Exception e) {
            fail("testCapacityLimit", e);
        }
    }

    static void testFIFOOrder() {
        try {
            BoundedBlockingQueue<Integer> queue = new BoundedBlockingQueue<>(5);
            
            for (int i = 1; i <= 5; i++) {
                queue.put(i);
            }
            
            for (int i = 1; i <= 5; i++) {
                assertEquals("FIFO order", i, queue.take());
            }
            
            pass("testFIFOOrder");
        } catch (Exception e) {
            fail("testFIFOOrder", e);
        }
    }

    static void testBlockingWhenFull() {
        try {
            BoundedBlockingQueue<Integer> queue = new BoundedBlockingQueue<>(2);
            
            // Fill the queue
            queue.put(1);
            queue.put(2);
            
            // Try to put when full - should block
            final boolean[] blocked = {false};
            Thread producer = new Thread(() -> {
                try {
                    blocked[0] = true;
                    queue.put(3); // Will block
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            
            producer.start();
            Thread.sleep(100); // Let producer block
            
            assertTrue("Producer should be blocked", blocked[0] && producer.isAlive());
            
            // Unblock by taking an item
            queue.take();
            producer.join(1000);
            
            assertFalse("Producer should finish", producer.isAlive());
            
            pass("testBlockingWhenFull");
        } catch (Exception e) {
            fail("testBlockingWhenFull", e);
        }
    }

    static void testBlockingWhenEmpty() {
        try {
            BoundedBlockingQueue<Integer> queue = new BoundedBlockingQueue<>(5);
            
            // Try to take from empty queue - should block
            final Integer[] result = {null};
            Thread consumer = new Thread(() -> {
                try {
                    result[0] = queue.take(); // Will block
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            
            consumer.start();
            Thread.sleep(100); // Let consumer block
            
            assertTrue("Consumer should be blocked", consumer.isAlive());
            
            // Unblock by putting an item
            queue.put(42);
            consumer.join(1000);
            
            assertEquals("Consumer should get value", 42, result[0]);
            
            pass("testBlockingWhenEmpty");
        } catch (Exception e) {
            fail("testBlockingWhenEmpty", e);
        }
    }

    static void testMultipleThreads() {
        try {
            BoundedBlockingQueue<Integer> queue = new BoundedBlockingQueue<>(10);
            List<Integer> consumed = new ArrayList<>();
            
            // Producer thread
            Thread producer = new Thread(() -> {
                try {
                    for (int i = 1; i <= 20; i++) {
                        queue.put(i);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            
            // Consumer thread
            Thread consumer = new Thread(() -> {
                try {
                    for (int i = 0; i < 20; i++) {
                        Integer value = queue.take();
                        synchronized (consumed) {
                            consumed.add(value);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            
            producer.start();
            consumer.start();
            
            producer.join(5000);
            consumer.join(5000);
            
            assertEquals("All items consumed", 20, consumed.size());
            
            pass("testMultipleThreads");
        } catch (Exception e) {
            fail("testMultipleThreads", e);
        }
    }

    static void testNullElements() {
        try {
            BoundedBlockingQueue<Integer> queue = new BoundedBlockingQueue<>(5);
            
            queue.put(null);
            assertEquals("Size should be 1", 1, queue.size());
            
            Integer value = queue.take();
            assertEquals("Should get null", null, value);
            
            pass("testNullElements");
        } catch (Exception e) {
            fail("testNullElements", e);
        }
    }

    // Helper methods
    static void assertEquals(String message, Object expected, Object actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " - Expected: " + expected + ", Actual: " + actual);
        }
    }

    static void assertTrue(String message, boolean condition) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void assertFalse(String message, boolean condition) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

    static void pass(String testName) {
        testsPassed++;
        System.out.println("✓ " + testName + " PASSED");
    }

    static void fail(String testName, Exception e) {
        testsFailed++;
        System.out.println("✗ " + testName + " FAILED: " + e.getMessage());
        e.printStackTrace();
    }
}