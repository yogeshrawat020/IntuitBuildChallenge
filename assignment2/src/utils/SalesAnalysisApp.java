package utils;
//package analysis;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Entry point: loads CSV, runs all analyses, and prints results to console.
 */
public class SalesAnalysisApp {

    public static void main(String[] args) {
        String csvPathStr = args.length > 0 ? args[0] : "data/sales.csv";
        Path csvPath = Path.of(csvPathStr);

        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();

        try {
            List<SalesRecord> records = analyzer.loadFromCsv(csvPath);
            System.out.println("Loaded records: " + records.size());
            System.out.println();

            // 1) Total revenue by region
            Map<String, Double> revenueByRegion = analyzer.totalRevenueByRegion(records);
            SalesDataAnalyzer.printSortedMap("Total Revenue by Region", revenueByRegion);

            // 2) Total revenue by item
            Map<String, Double> revenueByItem = analyzer.totalRevenueByItem(records);
            SalesDataAnalyzer.printSortedMap("Total Revenue by Item", revenueByItem);

            // 3) Average order value
            double avgOrderValue = analyzer.averageOrderValue(records);
            System.out.println("==== Average Order Value ====");
            System.out.println(avgOrderValue);
            System.out.println();

            // 4) Total revenue by year
            Map<Integer, Double> revenueByYear = analyzer.totalRevenueByYear(records);
            SalesDataAnalyzer.printSortedMap("Total Revenue by Year", revenueByYear);

            // 5) Top 5 items by revenue
            var topItems = analyzer.topItemsByRevenue(records, 5);
            SalesDataAnalyzer.printEntries("Top 5 Items by Revenue", topItems);

            // 6) Total units by country
            Map<String, Integer> unitsByCountry = analyzer.totalUnitsByCountry(records);
            SalesDataAnalyzer.printSortedMap("Total Units Sold by Country", unitsByCountry);

            // 7) Distinct items by region
            var itemsByRegion = analyzer.distinctItemsByRegion(records);
            SalesDataAnalyzer.printSortedMap("Distinct Items by Region", itemsByRegion);

            // 8) Example: total revenue for a specific region
            String region = "Europe";
            double europeRevenue = analyzer.totalRevenueForRegion(records, region);
            System.out.println("==== Total Revenue for Region '" + region + "' ====");
            System.out.println(europeRevenue);
            System.out.println();

        } catch (Exception e) {
            System.err.println("Error running analysis: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
