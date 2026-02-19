## CPU
The CPU, often referred to as the brain of the computer, is responsible for executing instructions from programs. It performs basic arithmetic, logic, control, and input/output operations specified by the instructions.

## Core
A core is an individual processing unit within a CPU. Modern CPUs can have multiple cores, allowing them to perform multiple tasks simultaneously.

A quad-core processor has four cores, allowing it to perform four tasks simultaneously. For instance, one core could handle your web browser, another your music player, another a download manager, and another a background system update.

## Program

A program is a set of instructions written in a programming language that tells the computer how to perform a specific task.
Microsoft Word is a program that allows users to create and edit documents.

## Process

A process is an instance of a program that is being executed. When a program runs, the operating system creates a process to manage its execution.

When we open Microsoft Word, it becomes a process in the operating system.

## Thread

A thread is the smallest unit of execution within a process. A process can have multiple threads, which share the same resources but can run independently.

A process is an independent, executing program with its own memory space (container), while a thread is a smaller, lightweight unit of execution within a process. Threads share the same memory, code, and resources of their parent process, making communication faster but less isolated than processes. 

Every tab in a chrome instance is a separate process not thread, because thread shares resource but process wont.

## Multitasking

Multitasking allows an operating system to run multiple processes simultaneously. On single-core CPUs, this is done through time-sharing, rapidly switching between tasks. On multi-core CPUs, true parallel execution occurs, with tasks distributed across cores. The OS scheduler balances the load, ensuring efficient and responsive system performance.

Multitasking utilizes the capabilities of a CPU and its cores. When an operating system performs multitasking, it can assign different tasks to different cores. This is more efficient than assigning all tasks to a single core.

## Multithreading

Multithreading refers to the ability to execute multiple threads within a single process concurrently.

A web browser can use multithreading by having separate threads for rendering the page, running JavaScript, and managing user inputs. This makes the browser more responsive and efficient.

Multithreading enhances the efficiency of multitasking by breaking down individual tasks into smaller sub-tasks or threads. These threads can be processed simultaneously, making better use of the CPU’s capabilities.

Multitasking can be achieved through multithreading where each task is divided into threads that are managed concurrently.

While multitasking typically refers to the running of multiple applications, multithreading is more granular, dealing with multiple threads within the same application or process.

### In a single-core system:

Both threads and processes are managed by the OS scheduler through time slicing and context switching to create the illusion of simultaneous execution.

### In a multi-core system:

Both threads and processes can run in true parallel on different cores, with the OS scheduler distributing tasks across the cores to optimize performance.


# Implement Threading in JAVA

### METHOD 1 : Extend the Thread class
1. A new class by the name `World` is created that extends Thread.
2. The run method is overridden to define the code that constitutes the new thread, and `start` method is called to initiate the new thread.
```java
public class Main {
    public static void main(String[] args) {
        World world = new World();
        world.start();
        for (; ; ) {
            System.out.println("Hello");
        }
    }
}
```
```java
public class World extends Thread {
    @Override
    public void run() {
        for (; ; ) {
            System.out.println("World");
        }
    }
}
```

### Method 2 : Implement Runnable Interface
1. A new class `World` is created that extends Thread.
2. The run method is overridden to define the code that constitutes the new thread.
3. A Thread object is created by passing an instance of World.
4. The `start` method is called on the Thread object to initiate the new thread.

```java
public class Main {
    public static void main(String[] args) {
        World world = new World();
        Thread thread = new Thread(world);
        thread.start();
        for (; ; ) {
            System.out.println("Hello");
        }
    }
}
```
```java
public class World implements Runnable {
    @Override
    public void run() {
        for (; ; ) {
            System.out.println("World");
        }
    }
}
```

## Thread Lifecycle
The lifecycle of a thread in Java consists of several states, which a thread can move through during its execution.

1. New: A thread is in this state when it is created but not yet started.
2. Runnable: After the start method is called, the thread becomes runnable. It’s ready to run and is waiting for CPU time.
3. Running: The thread is in this state when it is executing.
4. Blocked/Waiting: A thread is in this state when it is waiting for a resource or for another thread to perform an action.
5. Terminated: A thread is in this state when it has finished executing.

``` java
public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("RUNNING"); // RUNNING
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread t1 = new MyThread();
        System.out.println(t1.getState()); // NEW
        t1.start();
        System.out.println(t1.getState()); // RUNNABLE
        Thread.sleep(100);
        System.out.println(t1.getState()); // TIMED_WAITING
        t1.join();
        System.out.println(t1.getState()); // TERMINATED
    }
}
```

### Runnable vs Thread
Use Runnable when you want to separate the task from the thread, allowing the class to extend another class if needed. Extend Thread if you need to override Thread methods or if the task inherently requires direct control over the thread itself, though this limits inheritance.

Thread methods
start( ): Begins the execution of the thread. The Java Virtual Machine (JVM) calls the run() method of the thread.
run( ): The entry point for the thread. When the thread is started, the run() method is invoked. If the thread was created using a class that implements Runnable, the run() method will execute the run() method of that Runnable object.
sleep(long millis): Causes the currently executing thread to sleep (temporarily cease execution) for the specified number of milliseconds.
join( ): Waits for this thread to die. When one thread calls the join() method of another thread, it pauses the execution of the current thread until the thread being joined has completed its execution.
setPriority(int newPriority): Changes the priority of the thread. The priority is a value between Thread.MIN_PRIORITY (1) and Thread.MAX_PRIORITY (10).
public class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("Thread is Running...");
        for (int i = 1; i <= 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println(Thread.currentThread().getName() + " - Priority: " + Thread.currentThread().getPriority() + " - count: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        MyThread l = new MyThread("Low Priority Thread");
        MyThread m = new MyThread("Medium Priority Thread");
        MyThread n = new MyThread("High Priority Thread");
        l.setPriority(Thread.MIN_PRIORITY);
        m.setPriority(Thread.NORM_PRIORITY);
        n.setPriority(Thread.MAX_PRIORITY);
        l.start();
        m.start();
        n.start();

    }
}
6. interrupt(): Interrupts the thread. If the thread is blocked in a call to wait(), sleep(), or join(), it will throw an InterruptedException.


7. yield(): Thread.yield() is a static method that suggests the current thread temporarily pause its execution to allow other threads of the same or higher priority to execute. It’s important to note that yield() is just a hint to the thread scheduler, and the actual behavior may vary depending on the JVM and OS.

public class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " is running...");
            Thread.yield();
        }
    }

    public static void main(String[] args) {
        MyThread t1 = new MyThread(); 
        MyThread t2 = new MyThread();
        t1.start();
        t2.start();
    }
}
8. Thread.setDaemon(boolean): Marks the thread as either a daemon thread or a user thread. When the JVM exits, all daemon threads are terminated. Daemon threads in Java are the "background helper" threads. They are not the main/important threads of your program.
The single most important rule that makes everything clear:
The JVM exits (program ends) as soon as all non-daemon threads (also called user threads) have finished — even if some daemon threads are still running.
JVM does not wait for daemon threads to complete

public class MyThread extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println("Hello world! ");
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.setDaemon(true); // myThread is daemon thread ( like Garbage collector ) now
        MyThread t1 = new MyThread();
        t1.start(); // t1 is user thread
        myThread.start();
        System.out.println("Main Done");
    }
}
