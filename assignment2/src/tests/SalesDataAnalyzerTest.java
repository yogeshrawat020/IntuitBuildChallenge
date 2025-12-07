package tests;
import utils.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

/**
 * Unit tests for SalesDataAnalyzer - NO FRAMEWORKS NEEDED
 * Run with: java utils.SalesDataAnalyzerTest
 */
public class SalesDataAnalyzerTest {
    
    private static int passed = 0;
    private static int failed = 0;
    
    public static void main(String[] args) {
        System.out.println("Running SalesDataAnalyzer Tests...\n");
        
        // Run all tests
        testTotalRevenueByRegion();
        testTotalRevenueByItem();
        testAverageOrderValue();
        testAverageOrderValueEmptyList();
        testTotalRevenueByYear();
        testTopItemsByRevenue();
        testTopItemsByRevenueLimit();
        testTotalUnitsByCountry();
        testTotalRevenueForRegion();
        testTotalRevenueForRegionCaseInsensitive();
        testTotalRevenueForRegionNotFound();
        testDistinctItemsByRegion();
        testLoadFromCsv();
        testLoadFromCsvEmptyFile();
        testLoadFromCsvSkipsMalformed();
        
        // Print summary
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Test Results:");
        System.out.println("  Passed: " + passed);
        System.out.println("  Failed: " + failed);
        System.out.println("  Total:  " + (passed + failed));
        System.out.println("=".repeat(50));
        
        // Exit with error code if any tests failed
        if (failed > 0) {
            System.exit(1);
        }
    }
    
    // Helper method to create test data
    private static List<SalesRecord> createTestRecords() {
        return Arrays.asList(
            new SalesRecord("1", "Europe", "Germany", "Book", 
                LocalDate.of(2024, 1, 10), 10, 5.0),
            new SalesRecord("2", "Europe", "France", "Pen", 
                LocalDate.of(2024, 1, 15), 20, 2.0),
            new SalesRecord("3", "Asia", "Japan", "Book", 
                LocalDate.of(2024, 2, 1), 5, 5.0)
        );
    }
    
    // Assertion helpers
    private static void assertEquals(String testName, double expected, double actual, double delta) {
        if (Math.abs(expected - actual) <= delta) {
            System.out.println("✓ PASS: " + testName);
            passed++;
        } else {
            System.out.println("✗ FAIL: " + testName);
            System.out.println("  Expected: " + expected + ", Got: " + actual);
            failed++;
        }
    }
    
    private static void assertEquals(String testName, int expected, int actual) {
        if (expected == actual) {
            System.out.println("✓ PASS: " + testName);
            passed++;
        } else {
            System.out.println("✗ FAIL: " + testName);
            System.out.println("  Expected: " + expected + ", Got: " + actual);
            failed++;
        }
    }
    
    private static void assertEquals(String testName, String expected, String actual) {
        if (expected.equals(actual)) {
            System.out.println("✓ PASS: " + testName);
            passed++;
        } else {
            System.out.println("✗ FAIL: " + testName);
            System.out.println("  Expected: '" + expected + "', Got: '" + actual + "'");
            failed++;
        }
    }
    
    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("✓ PASS: " + testName);
            passed++;
        } else {
            System.out.println("✗ FAIL: " + testName);
            System.out.println("  Expected: true, Got: false");
            failed++;
        }
    }
    
    // Test methods
    private static void testTotalRevenueByRegion() {
        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();
        List<SalesRecord> records = createTestRecords();
        
        Map<String, Double> result = analyzer.totalRevenueByRegion(records);
        
        assertEquals("totalRevenueByRegion - Europe revenue", 90.0, result.get("Europe"), 0.01);
        assertEquals("totalRevenueByRegion - Asia revenue", 25.0, result.get("Asia"), 0.01);
        assertEquals("totalRevenueByRegion - map size", 2, result.size());
    }
    
    private static void testTotalRevenueByItem() {
        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();
        List<SalesRecord> records = createTestRecords();
        
        Map<String, Double> result = analyzer.totalRevenueByItem(records);
        
        assertEquals("totalRevenueByItem - Book revenue", 75.0, result.get("Book"), 0.01);
        assertEquals("totalRevenueByItem - Pen revenue", 40.0, result.get("Pen"), 0.01);
        assertEquals("totalRevenueByItem - map size", 2, result.size());
    }
    
    private static void testAverageOrderValue() {
        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();
        List<SalesRecord> records = createTestRecords();
        
        double avg = analyzer.averageOrderValue(records);
        
        // Total: 50 + 40 + 25 = 115, divided by 3 = 38.33
        assertEquals("averageOrderValue", 38.33, avg, 0.01);
    }
    
    private static void testAverageOrderValueEmptyList() {
        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();
        
        double avg = analyzer.averageOrderValue(Collections.emptyList());
        
        assertEquals("averageOrderValue with empty list", 0.0, avg, 0.01);
    }
    
    private static void testTotalRevenueByYear() {
        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();
        List<SalesRecord> records = createTestRecords();
        
        Map<Integer, Double> result = analyzer.totalRevenueByYear(records);
        
        assertEquals("totalRevenueByYear - 2024 revenue", 115.0, result.get(2024), 0.01);
        assertEquals("totalRevenueByYear - map size", 1, result.size());
    }
    
    private static void testTopItemsByRevenue() {
        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();
        List<SalesRecord> records = createTestRecords();
        
        List<Map.Entry<String, Double>> top = analyzer.topItemsByRevenue(records, 2);
        
        assertEquals("topItemsByRevenue - list size", 2, top.size());
        assertEquals("topItemsByRevenue - top item", "Book", top.get(0).getKey());
        assertEquals("topItemsByRevenue - top revenue", 75.0, top.get(0).getValue(), 0.01);
    }
    
    private static void testTopItemsByRevenueLimit() {
        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();
        List<SalesRecord> records = createTestRecords();
        
        List<Map.Entry<String, Double>> top = analyzer.topItemsByRevenue(records, 1);
        
        assertEquals("topItemsByRevenue with limit 1", 1, top.size());
    }
    
    private static void testTotalUnitsByCountry() {
        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();
        List<SalesRecord> records = createTestRecords();
        
        Map<String, Integer> result = analyzer.totalUnitsByCountry(records);
        
        assertEquals("totalUnitsByCountry - Germany", 10, result.get("Germany"));
        assertEquals("totalUnitsByCountry - France", 20, result.get("France"));
        assertEquals("totalUnitsByCountry - Japan", 5, result.get("Japan"));
        assertEquals("totalUnitsByCountry - map size", 3, result.size());
    }
    
    private static void testTotalRevenueForRegion() {
        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();
        List<SalesRecord> records = createTestRecords();
        
        double revenue = analyzer.totalRevenueForRegion(records, "Europe");
        
        assertEquals("totalRevenueForRegion - Europe", 90.0, revenue, 0.01);
    }
    
    private static void testTotalRevenueForRegionCaseInsensitive() {
        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();
        List<SalesRecord> records = createTestRecords();
        
        double revenue = analyzer.totalRevenueForRegion(records, "europe");
        
        assertEquals("totalRevenueForRegion - case insensitive", 90.0, revenue, 0.01);
    }
    
    private static void testTotalRevenueForRegionNotFound() {
        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();
        List<SalesRecord> records = createTestRecords();
        
        double revenue = analyzer.totalRevenueForRegion(records, "Antarctica");
        
        assertEquals("totalRevenueForRegion - not found", 0.0, revenue, 0.01);
    }
    
    private static void testDistinctItemsByRegion() {
        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();
        List<SalesRecord> records = createTestRecords();
        
        Map<String, Set<String>> result = analyzer.distinctItemsByRegion(records);
        
        assertEquals("distinctItemsByRegion - map size", 2, result.size());
        assertTrue("distinctItemsByRegion - Europe has Book", result.get("Europe").contains("Book"));
        assertTrue("distinctItemsByRegion - Europe has Pen", result.get("Europe").contains("Pen"));
        assertEquals("distinctItemsByRegion - Asia set size", 1, result.get("Asia").size());
    }
    
    private static void testLoadFromCsv() {
        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();
        
        try {
            // Create temporary CSV file
            Path tempFile = Files.createTempFile("test", ".csv");
            String csvContent = 
                "orderId,region,country,item,orderDate,units,unitPrice\n" +
                "1,Europe,Germany,Book,2024-01-10,10,5.0\n" +
                "2,Asia,Japan,Pen,2024-01-15,20,2.0\n";
            
            Files.writeString(tempFile, csvContent);
            
            List<SalesRecord> records = analyzer.loadFromCsv(tempFile);
            
            assertEquals("loadFromCsv - record count", 2, records.size());
            assertEquals("loadFromCsv - first orderId", "1", records.get(0).getOrderId());
            assertEquals("loadFromCsv - first region", "Europe", records.get(0).getRegion());
            
            // Clean up
            Files.delete(tempFile);
            
        } catch (IOException e) {
            System.out.println("✗ FAIL: loadFromCsv - " + e.getMessage());
            failed++;
        }
    }
    
    private static void testLoadFromCsvEmptyFile() {
        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();
        
        try {
            Path tempFile = Files.createTempFile("empty", ".csv");
            Files.writeString(tempFile, "");
            
            List<SalesRecord> records = analyzer.loadFromCsv(tempFile);
            
            assertEquals("loadFromCsv empty file - record count", 0, records.size());
            
            Files.delete(tempFile);
            
        } catch (IOException e) {
            System.out.println("✗ FAIL: loadFromCsv empty file - " + e.getMessage());
            failed++;
        }
    }
    
    private static void testLoadFromCsvSkipsMalformed() {
        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();
        
        try {
            Path tempFile = Files.createTempFile("malformed", ".csv");
            String csvContent = 
                "orderId,region,country,item,orderDate,units,unitPrice\n" +
                "1,Europe,Germany,Book,2024-01-10,10,5.0\n" +
                "BAD_LINE_MISSING_FIELDS\n" +
                "2,Asia,Japan,Pen,2024-01-15,20,2.0\n";
            
            Files.writeString(tempFile, csvContent);
            
            List<SalesRecord> records = analyzer.loadFromCsv(tempFile);
            
            // Should skip malformed line and load 2 valid records
            assertEquals("loadFromCsv with malformed lines", 2, records.size());
            
            Files.delete(tempFile);
            
        } catch (IOException e) {
            System.out.println("✗ FAIL: loadFromCsv malformed - " + e.getMessage());
            failed++;
        }
    }
}