# Intuit Build Challenges - Java Solutions

This repository contains solutions to two coding challenges demonstrating core Java programming competencies.

## 📋 Table of Contents
- [Overview](#overview)
- [Assignments](#assignments)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [Testing](#testing)
- [Technologies](#technologies)

## Overview

This project implements two distinct coding challenges:

1. **Producer-Consumer Pattern** - Concurrent programming with thread synchronization
2. **Sales Data Analysis** - Functional programming with Java Streams API

Both assignments demonstrate best practices, comprehensive testing, and clean code architecture.

## Assignments

### Assignment 1: Producer-Consumer Pattern
**Objectives:**
- ✅ Thread synchronization using `synchronized`, `wait()`, `notifyAll()`
- ✅ Concurrent programming with multiple threads
- ✅ Custom blocking queue implementation
- ✅ Wait/Notify mechanism for thread coordination

[View detailed documentation →](assignment1/README.md)

**Key Features:**
- Custom BoundedBlockingQueue with automatic blocking
- Thread-safe producer/consumer implementation
- Poison pill pattern for clean shutdown
- Generic implementation works with any type

### Assignment 2: Sales Data Analysis
**Objectives:**
- ✅ Functional programming paradigms
- ✅ Stream API operations (map, filter, collect, groupBy)
- ✅ Data aggregation and grouping
- ✅ Lambda expressions and method references

[View detailed documentation →](assignment2/README.md)

**Key Features:**
- CSV data loading with error handling
- Multiple aggregation operations using Streams
- Immutable data models
- No external dependencies

## Project Structure

```
build-challenges/
├── README.md                          # This file
├── assignment1/                       # Producer-Consumer Pattern
│   ├── README.md                     # Assignment 1 documentation
│   ├── src/
│   │   ├── App.java                  # Demo application
│   │   ├── utils/
│   │   │   ├── BoundedBlockingQueue.java
│   │   │   ├── Producer.java
│   │   │   └── Consumer.java
│   │   └── tests/
│   │       ├── TestBoundedBlockingQueue.java
│   │       └── TestProducerConsumer.java
│   └── bin/                          # Compiled classes
│
└── assignment2/                       # Sales Data Analysis
    ├── README.md                     # Assignment 2 documentation
    ├── src/
    │   ├── App.java                  # Main entry point
    │   ├── utils/
    │   │   ├── SalesDataAnalyzer.java
    │   │   ├── SalesRecord.java
    │   │   └── SalesAnalysisApp.java
    │   ├── tests/
    │   │   └── SalesDataAnalyzerTest.java
    │   └── resources/
    │       └── sales.csv             # Sample data
    └── bin/                          # Compiled classes
```

## Prerequisites

- **Java 11 or higher**
- No external dependencies or frameworks required
- Works with standard JDK only

## Quick Start

### Clone the Repository
```bash
git clone https://github.com/yogeshrawat020/IntuitBuildChallenge.git
cd IntuitBuildChallenge
```

### Run Assignment 1 (Producer-Consumer)
```bash
cd assignment1
javac -d bin src/App.java src/utils/*.java
java -cp bin App
```

**Expected Output:**
```
=== Producer-Consumer Pattern Demo ===
Using BoundedBlockingQueue with wait/notify mechanism

Initial Configuration:
  Source items: 10
  Queue capacity: 3
  Starting threads...

Producer-1 produced: 1 (Queue size: 1/3)
Consumer-1 consumed: 1 (Queue size: 0/3)
...
✓ SUCCESS: All items transferred correctly!
```

### Run Assignment 2 (Sales Analysis)
```bash
cd assignment2
javac -d bin src/App.java src/utils/*.java
java -cp bin App
```

**Expected Output:**
```
Loaded 10 records

==== Total Revenue by Region ====
Asia -> 328.0
Europe -> 377.5
North America -> 260.0
South America -> 100.0

==== Total Revenue by Item ====
Book -> 514.0
Notebook -> 258.0
...
```

## Testing

Both assignments include comprehensive unit tests using **pure Java** (no external frameworks).

### Test Assignment 1
```bash
cd assignment1
javac -d bin src/App.java src/utils/*.java src/tests/*.java
java -cp bin tests.TestBoundedBlockingQueue
java -cp bin tests.TestProducerConsumer
```

**Coverage:**
- 7 unit tests for BoundedBlockingQueue
- 5 integration tests for Producer-Consumer
- Tests for thread safety, blocking behavior, and edge cases

### Test Assignment 2
```bash
cd assignment2
javac -d bin src/tests/SalesDataAnalyzerTest.java src/utils/*.java
java -cp bin tests.SalesDataAnalyzerTest
```

**Coverage:**
- 15 unit tests covering all analysis methods
- Tests for CSV parsing, aggregation, edge cases
- Validation of Stream operations and lambda expressions

## Technologies

- **Java 11+**
  - Streams API
  - Lambda expressions
  - Method references
  - Thread synchronization (synchronized, wait, notify)
  - NIO Files API
  - LocalDate and DateTimeFormatter

- **Pure Java Testing**
  - No JUnit or external frameworks
  - Custom assertion methods
  - Simple test runners with pass/fail tracking

## Key Concepts Demonstrated

### Concurrent Programming (Assignment 1)
- Thread synchronization with `synchronized` blocks
- Inter-thread communication using `wait()` and `notifyAll()`
- Bounded blocking queue implementation
- Producer-Consumer design pattern
- Thread-safe data structures
- Graceful shutdown with poison pill pattern

### Functional Programming (Assignment 2)
- Stream operations: `map()`, `filter()`, `collect()`
- Collectors: `groupingBy()`, `summingDouble()`, `summingInt()`
- Lambda expressions and closures
- Method references (`::`)
- Immutable data structures
- Declarative vs imperative programming

## Code Quality

✅ **Clean Code**
- Descriptive variable and method names
- Comprehensive inline comments
- Proper exception handling
- Clear separation of concerns

✅ **Best Practices**
- Immutable classes where appropriate
- Generic types for reusability
- Defensive programming
- Thread-safe implementations

✅ **Documentation**
- Detailed README for each assignment
- Javadoc-style comments
- Sample outputs and usage examples
- Clear setup instructions

## Running Everything at Once

To compile and run all assignments with tests:

```bash
# From repository root
./run_all.sh
```

Or manually:

```bash
# Assignment 1
cd assignment1
javac -d bin src/**/*.java
java -cp bin App
java -cp bin tests.TestBoundedBlockingQueue
java -cp bin tests.TestProducerConsumer

# Assignment 2
cd ../assignment2
javac -d bin src/**/*.java
java -cp bin App
java -cp bin tests.SalesDataAnalyzerTest
```

## Author

[Yogesh Rawat]

## License
This project was created as a submission for the Intuit Build Challenge.

## Acknowledgments

Solutions implement requirements specified in the Intuit Build Challenge documentation.