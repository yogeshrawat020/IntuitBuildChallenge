package utils;
//package analysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Performs all CSV loading and analytical queries using Streams and lambdas.
 */
public class SalesDataAnalyzer {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Expected CSV headers (case-insensitive):
     * orderId, region, country, item, orderDate, units, unitPrice
     */
    public List<SalesRecord> loadFromCsv(Path csvPath) throws IOException {
        try (Stream<String> lines = Files.lines(csvPath)) {
            List<String> all = lines
                    .filter(line -> !line.trim().isEmpty())
                    .collect(Collectors.toList());

            if (all.isEmpty()) {
                return List.of();
            }

            String headerLine = all.get(0);
            Map<String, Integer> headerIndex = parseHeader(headerLine);

            return all.subList(1, all.size()).stream()
                    .map(line -> parseRecord(line, headerIndex))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
    }

    private Map<String, Integer> parseHeader(String headerLine) {
        String[] headers = headerLine.split(",");
        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            indexMap.put(headers[i].trim().toLowerCase(), i);
        }
        return indexMap;
    }

    private SalesRecord parseRecord(String line, Map<String, Integer> headerIndex) {
        String[] parts = line.split(",");
        // Defensive: ignore malformed lines
        try {
            String orderId = get(parts, headerIndex, "orderid");
            String region = get(parts, headerIndex, "region");
            String country = get(parts, headerIndex, "country");
            String item = get(parts, headerIndex, "item");
            String orderDateStr = get(parts, headerIndex, "orderdate");
            String unitsStr = get(parts, headerIndex, "units");
            String unitPriceStr = get(parts, headerIndex, "unitprice");

            LocalDate orderDate = LocalDate.parse(orderDateStr.trim(), DATE_FORMAT);
            int units = Integer.parseInt(unitsStr.trim());
            double unitPrice = Double.parseDouble(unitPriceStr.trim());

            return new SalesRecord(orderId, region, country, item, orderDate, units, unitPrice);
        } catch (Exception e) {
            System.err.println("Skipping malformed line: " + line + " -> " + e.getMessage());
            return null;
        }
    }

    private String get(String[] parts, Map<String, Integer> headerIndex, String key) {
        Integer idx = headerIndex.get(key);
        if (idx == null || idx < 0 || idx >= parts.length) {
            throw new IllegalArgumentException("Missing column: " + key);
        }
        return parts[idx];
    }

    // ================== ANALYSIS METHODS (all using Streams) ==================

    /**
     * Total revenue per region.
     */
    public Map<String, Double> totalRevenueByRegion(List<SalesRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getRegion,
                        Collectors.summingDouble(SalesRecord::getRevenue)
                ));
    }

    /**
     * Total revenue per item/product.
     */
    public Map<String, Double> totalRevenueByItem(List<SalesRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getItem,
                        Collectors.summingDouble(SalesRecord::getRevenue)
                ));
    }

    /**
     * Average order value across all records.
     */
    public double averageOrderValue(List<SalesRecord> records) {
        return records.stream()
                .mapToDouble(SalesRecord::getRevenue)
                .average()
                .orElse(0.0);
    }

    /**
     * Total revenue per year.
     */
    public Map<Integer, Double> totalRevenueByYear(List<SalesRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        record -> record.getOrderDate().getYear(),
                        Collectors.summingDouble(SalesRecord::getRevenue)
                ));
    }

    /**
     * Top N items by total revenue, descending.
     */
    public List<Map.Entry<String, Double>> topItemsByRevenue(List<SalesRecord> records, int limit) {
        return totalRevenueByItem(records).entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Generic grouping example: group by country and compute total units sold.
     */
    public Map<String, Integer> totalUnitsByCountry(List<SalesRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getCountry,
                        Collectors.summingInt(SalesRecord::getUnits)
                ));
    }

    /**
     * Example of functional-style filtering + aggregation:
     * total revenue for a given region.
     */
    public double totalRevenueForRegion(List<SalesRecord> records, String region) {
        return records.stream()
                .filter(r -> r.getRegion().equalsIgnoreCase(region))
                .mapToDouble(SalesRecord::getRevenue)
                .sum();
    }

    /**
     * Return a map of region -> set of distinct items sold there.
     */
    public Map<String, Set<String>> distinctItemsByRegion(List<SalesRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getRegion,
                        Collectors.mapping(SalesRecord::getItem, Collectors.toSet())
                ));
    }

    /**
     * Convenience method to print a map in sorted key order.
     */
    public static <K extends Comparable<K>, V> void printSortedMap(String title, Map<K, V> map) {
        System.out.println("==== " + title + " ====");
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));
        System.out.println();
    }

    /**
     * Convenience method to print a list of entries.
     */
    public static <K, V> void printEntries(String title, List<Map.Entry<K, V>> entries) {
        System.out.println("==== " + title + " ====");
        entries.forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));
        System.out.println();
    }
}
