import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import utils.SalesDataAnalyzer;
import utils.SalesRecord;

public class App {
    public static void main(String[] args) throws Exception {
        //System.out.println("Hello, World!");
         // If user passes CSV path as argument → use it
        // Else default path: src/main/resources/sales.csv
        String csvPathStr = args.length > 0 ?
                args[0] :
                "src/resources/sales.csv";

        Path csvPath = Path.of(csvPathStr);

        SalesDataAnalyzer analyzer = new SalesDataAnalyzer();

        try {
            // Load CSV data
            List<SalesRecord> records = analyzer.loadFromCsv(csvPath);
            System.out.println("Loaded " + records.size() + " records\n");

            // Run all analyses
            Map<String, Double> revenueByRegion = analyzer.totalRevenueByRegion(records);
            Map<String, Double> revenueByItem   = analyzer.totalRevenueByItem(records);
            Map<Integer, Double> revenueByYear = analyzer.totalRevenueByYear(records);

            double avgOrderValue = analyzer.averageOrderValue(records);

            // Print results
            SalesDataAnalyzer.printSortedMap("Total Revenue by Region", revenueByRegion);
            SalesDataAnalyzer.printSortedMap("Total Revenue by Item", revenueByItem);
            SalesDataAnalyzer.printSortedMap("Total Revenue by Year", revenueByYear);

            System.out.println("==== Average Order Value ====");
            System.out.println(avgOrderValue + "\n");

            // Top 3 items by revenue
            var top = analyzer.topItemsByRevenue(records, 3);
            SalesDataAnalyzer.printEntries("Top 3 Items by Revenue", top);

        } catch (Exception e) {
            System.err.println("Error running analysis: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
