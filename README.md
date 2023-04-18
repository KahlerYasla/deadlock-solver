# Code Report

## Introduction

The given code implements a simple example of synchronization in Java using semaphores. The main goal of this code is to demonstrate the concept of read-write locks, where multiple readers can access the shared resource simultaneously, but only one writer can access the resource at a time.

## Code Walkthrough

### The `sync` Class

This class serves as the entry point for the program. It creates an instance of the `ExecutorService` class and a `ReadWriteLock` object. After that, it creates four instances of the `Writer` class and four instances of the `Reader` class, passing them the `ReadWriteLock` object as a parameter. Finally, it submits all the tasks to the executor service, which runs them concurrently.

### The `ReadWriteLock` Class

This class implements the read-write lock mechanism. It has three methods for acquiring and releasing the lock for reading and writing. It uses two semaphores: `mutex` and `S`. `mutex` ensures that only one thread can access the `readerCount` variable at a time, and `S` ensures that only one writer can access the shared resource at a time.

The `readLock` method acquires the `mutex` semaphore and increments the `readerCount` variable. If it is the first reader, it acquires the `S` semaphore, allowing other readers to read but blocking any writers from accessing the resource. It then releases the `mutex` semaphore.

The `writeLock` method acquires the `S` semaphore, blocking any readers or writers from accessing the resource. It then prints a message indicating that the writer is writing.

The `readUnLock` method acquires the `mutex` semaphore and decrements the `readerCount` variable. If it is the last reader, it releases the `S` semaphore, allowing writers to access the resource. It then releases the `mutex` semaphore.

The `writeUnLock` method prints a message indicating that the writer is done writing and releases the `S` semaphore, allowing other writers to access the resource.

### The `SleepUtilities` Class

This class contains a utility method `nap` that causes the current thread to sleep for a random amount of time between 0 and 5 seconds.

### The `Writer` Class

This class implements the `Runnable` interface and represents a writer thread. It has a `ReadWriteLock` object and a `writerNum` variable as instance variables.

The `run` method is an infinite loop that sleeps for a random amount of time, attempts to acquire the write lock, sleeps again, and releases the write lock.

### The `Reader` Class

This class implements the `Runnable` interface and represents a reader thread. It has a `ReadWriteLock` object and a `readerNum` variable as instance variables.

The `run` method is an infinite loop that sleeps for a random amount of time, attempts to acquire the read lock, sleeps again, and releases the read lock.

## Conclusion

In conclusion, this code implements a simple example of synchronization in Java using semaphores. It demonstrates the concept of read-write locks and how multiple readers can access the shared resource simultaneously, but only one writer can access the resource at a time.
