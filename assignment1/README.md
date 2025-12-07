# Assignment 1: Producer-Consumer Pattern

## Overview
Implementation of the classic Producer-Consumer pattern using Java's wait/notify mechanism for thread synchronization.

## Requirements Met
✅ Thread synchronization using `synchronized`, `wait()`, and `notifyAll()`  
✅ Concurrent programming with multiple threads  
✅ Custom BoundedBlockingQueue implementation  
✅ Wait/Notify mechanism for thread coordination  
✅ Comprehensive testing  

## Project Structure
```
src/
├── App.java                          # Main demo application
├── utils/
│   ├── BoundedBlockingQueue.java    # Custom blocking queue
│   ├── Producer.java                 # Producer thread
│   └── Consumer.java                 # Consumer thread
└── tests/
    ├── TestBoundedBlockingQueue.java    # Queue tests
    └── TestProducerConsumer.java        # Integration tests
```

## How to Run

### Run Demo
```bash
javac -d bin src/App.java src/utils/*.java
java -cp bin App
```

### Run Tests
```bash
javac -d bin src/App.java src/utils/*.java src/tests/*.java
java -cp bin tests.TestBoundedBlockingQueue
java -cp bin tests.TestProducerConsumer
```

## Key Features
- **BoundedBlockingQueue**: Custom implementation using wait/notify
- **Thread Synchronization**: Automatic blocking when full/empty
- **Poison Pill Pattern**: Clean shutdown mechanism
- **Generic Types**: Works with any object type

## Output Example
```
=== Producer-Consumer Pattern Demo ===
Producer-1 produced: 1 (Queue size: 1/3)
Consumer-1 consumed: 1 (Queue size: 0/3)
...
✓ SUCCESS: All items transferred correctly!
```

## Testing
- 7 unit tests for BoundedBlockingQueue
- 5 integration tests for Producer-Consumer
- All tests passing ✓