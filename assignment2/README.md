# Assignment 2: Sales Data Analysis Application

## Overview

Java application demonstrating Stream API proficiency through comprehensive sales data analysis using functional programming paradigms.

## Features

* CSV data loading with robust error handling
* Multiple aggregation operations using Java Streams
* Grouping, filtering, and mapping operations
* Lambda expressions throughout
* Functional programming best practices
* Comprehensive unit tests (no external frameworks needed)

## Project Structure

```
assignment2/
├── src/
│   ├── App.java                        # Main entry point
│   ├── utils/
│   │   ├── SalesDataAnalyzer.java      # Core analysis logic
│   │   ├── SalesRecord.java            # Immutable data model
│   │   └── SalesAnalysisApp.java       # Alternative entry point
│   └── tests/
│       └── SalesDataAnalyzerTest.java  # Unit tests (no frameworks)
├── resources/
│   └── sales.csv                       # Sample sales data
└── README.md
```

## Setup Instructions

### Prerequisites

* Java 11 or higher
* No external dependencies required

### Compilation

```bash
# Compile all source files
javac -d bin src/App.java src/utils/*.java src/tests/SalesDataAnalyzerTest.java
```

### Running the Application

```bash
# Run with default CSV path (src/resources/sales.csv)
java -cp bin App

# Run with custom CSV path
java -cp bin App /path/to/your/sales.csv
```

### Running Tests

```bash
# Run tests
java -cp bin tests.SalesDataAnalyzerTest
```

**Expected test output:**

```
Running SalesDataAnalyzer Tests...

✓ PASS: totalRevenueByRegion - Europe revenue
✓ PASS: totalRevenueByRegion - Asia revenue
✓ PASS: totalRevenueByRegion - map size
...
==================================================
Test Results:
  Passed: 15
  Failed: 0
  Total:  15
==================================================
```

## CSV Data Format

Expected columns (case-insensitive):

```
orderId,region,country,item,orderDate,units,unitPrice
```

Example:

```csv
1001,Europe,Germany,Book,2024-01-10,12,9.50
1002,Asia,India,Pen,2024-01-12,50,1.20
```

## Analysis Methods

The application performs the following analyses:

1. **Total Revenue by Region** — Aggregate revenue grouped by geographic region
2. **Total Revenue by Item** — Revenue breakdown by product
3. **Average Order Value** — Mean revenue across all orders
4. **Total Revenue by Year** — Annual revenue aggregation
5. **Top N Items by Revenue** — Ranking products by total revenue
6. **Total Units by Country** — Units sold per country
7. **Distinct Items by Region** — Unique products sold in each region
8. **Revenue for Specific Region** — Filtered aggregation example

## Sample Output

```
Loaded 10 records

==== Total Revenue by Region ====
Asia -> 328.0
Europe -> 377.5
North America -> 260.0
South America -> 100.0

==== Total Revenue by Item ====
Book -> 514.0
Marker -> 100.0
Notebook -> 258.0
Pen -> 137.0
Pencil -> 130.0

==== Total Revenue by Year ====
2024 -> 1065.5

==== Average Order Value ====
106.55

==== Top 3 Items by Revenue ====
Book -> 514.0
Notebook -> 258.0
Pen -> 137.0
```

## Key Stream Operations Demonstrated

* `stream()` — Create streams from collections
* `filter()` — Filter records by criteria
* `map()` / `mapToDouble()` — Transform elements
* `collect()` — Terminal operations with Collectors
* `groupingBy()` — Group elements by classifier
* `summingDouble()` / `summingInt()` — Aggregate numerical values
* `sorted()` — Sort stream elements
* `limit()` — Restrict stream size
* `average()` / `sum()` — Statistical operations

## Lambda Expressions Used

Examples:

```java
// Method reference
.collect(Collectors.groupingBy(SalesRecord::getRegion))

// Lambda for filtering
.filter(r -> r.getRegion().equalsIgnoreCase(region))

// Lambda for extraction
record -> record.getOrderDate().getYear()

// Comparator with method reference
.sorted(Map.Entry.<String, Double>comparingByValue().reversed())
```

## Testing

### Test Coverage

Comprehensive unit tests cover:

* All analysis methods (totalRevenueByRegion, totalRevenueByItem, etc.)
* Edge cases (empty lists, missing regions)
* CSV parsing (valid, malformed, empty files)
* Aggregation accuracy
* Case-insensitive filtering

### Running Tests

```bash
java -cp bin tests.SalesDataAnalyzerTest
```

Tests use plain assertions — **no external testing frameworks required**.

## Technologies

* Java 11+ (Streams API, LocalDate, NIO Files)
* Pure Java testing (no frameworks)
* Functional programming paradigms

## Implementation Notes

* All analysis methods use Java Streams — no traditional loops
* Immutable `SalesRecord` class for safety
* CSV parsing is defensive and skips malformed lines
* Case-insensitive region filtering
* Runs with standard JDK only

## Error Handling

* Malformed CSV lines logged to stderr
* Empty files produce empty result sets
* Missing columns detected and reported
* File I/O errors handled gracefully

## GitHub Repository

[https://github.com/yogeshrawat020/IntuitBuildChallenge/](https://github.com/yogeshrawat020/IntuitBuildChallenge/)

