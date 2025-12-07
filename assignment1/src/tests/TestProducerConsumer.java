package tests;
import utils.BoundedBlockingQueue;
import utils.Producer;
import utils.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestProducerConsumer {
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("=== Testing Producer-Consumer Integration ===\n");
        
        testCompleteWorkflow();
        testSmallQueue();
        testLargeQueue();
        testEmptySource();
        testLargeDataset();
        
        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + testsPassed);
        System.out.println("Failed: " + testsFailed);
        
        if (testsFailed == 0) {
            System.out.println("\n✓ ALL TESTS PASSED!");
        } else {
            System.out.println("\n✗ SOME TESTS FAILED!");
        }
    }

    static void testCompleteWorkflow() {
        try {
            List<Integer> source = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            List<Integer> destination = Collections.synchronizedList(new ArrayList<>());
            BoundedBlockingQueue<Integer> queue = new BoundedBlockingQueue<>(3);

            Thread producer = new Thread(new Producer<>(queue, source, "TestProducer"));
            Thread consumer = new Thread(new Consumer<>(queue, destination, "TestConsumer"));

            producer.start();
            consumer.start();

            producer.join(5000);
            consumer.join(5000);

            assertEquals("All items transferred", 10, destination.size());
            assertEquals("Items match", source, destination);
            
            pass("testCompleteWorkflow");
        } catch (Exception e) {
            fail("testCompleteWorkflow", e);
        }
    }

    static void testSmallQueue() {
        try {
            List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);
            List<Integer> destination = Collections.synchronizedList(new ArrayList<>());
            BoundedBlockingQueue<Integer> queue = new BoundedBlockingQueue<>(1); // Very small

            Thread producer = new Thread(new Producer<>(queue, source));
            Thread consumer = new Thread(new Consumer<>(queue, destination));

            producer.start();
            consumer.start();

            producer.join(5000);
            consumer.join(5000);

            assertEquals("Items match with small queue", source, destination);
            
            pass("testSmallQueue");
        } catch (Exception e) {
            fail("testSmallQueue", e);
        }
    }

    static void testLargeQueue() {
        try {
            List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);
            List<Integer> destination = Collections.synchronizedList(new ArrayList<>());
            BoundedBlockingQueue<Integer> queue = new BoundedBlockingQueue<>(100); // Very large

            Thread producer = new Thread(new Producer<>(queue, source));
            Thread consumer = new Thread(new Consumer<>(queue, destination));

            producer.start();
            consumer.start();

            producer.join(5000);
            consumer.join(5000);

            assertEquals("Items match with large queue", source, destination);
            
            pass("testLargeQueue");
        } catch (Exception e) {
            fail("testLargeQueue", e);
        }
    }

    static void testEmptySource() {
        try {
            List<Integer> source = new ArrayList<>();
            List<Integer> destination = Collections.synchronizedList(new ArrayList<>());
            BoundedBlockingQueue<Integer> queue = new BoundedBlockingQueue<>(5);

            Thread producer = new Thread(new Producer<>(queue, source));
            Thread consumer = new Thread(new Consumer<>(queue, destination));

            producer.start();
            consumer.start();

            producer.join(2000);
            consumer.join(2000);

            assertTrue("Destination should be empty", destination.isEmpty());
            
            pass("testEmptySource");
        } catch (Exception e) {
            fail("testEmptySource", e);
        }
    }

    static void testLargeDataset() {
        try {
            List<Integer> source = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                source.add(i);
            }

            List<Integer> destination = Collections.synchronizedList(new ArrayList<>());
            BoundedBlockingQueue<Integer> queue = new BoundedBlockingQueue<>(10);

            Thread producer = new Thread(new Producer<>(queue, source));
            Thread consumer = new Thread(new Consumer<>(queue, destination));

            producer.start();
            consumer.start();

            producer.join(10000);
            consumer.join(10000);

            assertEquals("All 100 items transferred", 100, destination.size());
            assertEquals("Items match", source, destination);
            
            pass("testLargeDataset");
        } catch (Exception e) {
            fail("testLargeDataset", e);
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

    static void pass(String testName) {
        testsPassed++;
        System.out.println("✓ " + testName + " PASSED");
    }

    static void fail(String testName, Exception e) {
        testsFailed++;
        System.out.println("✗ " + testName + " FAILED: " + e.getMessage());
    }
}