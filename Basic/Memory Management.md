**Java Memory Management – Quick Reference for Interviews**  
(Intermediate Level)


# Java Memory Management – Heap, Stack & Garbage Collection

## 1. Main Memory Areas in JVM

| Area              | What is stored                              | Lifetime                          | Managed by          | Speed     |
|-------------------|---------------------------------------------|-----------------------------------|---------------------|-----------|
| **Stack**         | Local variables, method parameters, references, return addresses | Method/block scope (very short)   | Automatically (LIFO) | Very fast |
| **Heap**          | All objects + instance variables            | Until GC collects them            | Garbage Collector   | Slower    |
| **Metaspace**     | Class metadata, static variables, constants | Until class is unloaded (rare)    | GC + class unloading| —         |
| **PC Register**   | Current instruction being executed          | Thread lifetime                   | JVM                 | —         |
| **Native Method Stack** | Native method calls (JNI)              | Similar to Java stack             | JVM / OS            | —         |

## 2. Key Differences – Stack vs Heap

```text
Feature                  Stack                          Heap
------------------------ ------------------------------ --------------------------------
Memory allocation        Static / compile-time-ish     Dynamic (runtime)
Deallocation             Automatic (scope ends)        Manual → Garbage Collected
Access time              Very fast                      Slower
Size                     Usually smaller (few MB)       Much larger (hundreds MB – tens GB)
Structure                LIFO (contiguous blocks)      Free-form, fragmented
Thread safety            Each thread has own stack     Shared between threads (needs synchronization)
OutOfMemoryError         StackOverflowError            java.lang.OutOfMemoryError: Java heap space
Typical content          primitives + object references   objects themselves + arrays
```

## 3. Most Important Rules to Remember

- All **objects** live in **Heap**
- All **object references** can live in:
  - Stack (local variables)
  - Heap (instance & static fields)
  - Metaspace (static fields of classes)
- **Primitives** (int, long, double, boolean…) live directly in the place where declared
  
  → local → stack  
  → fields → heap  
  → static → metaspace

## 4. Garbage Collection – Simplified View

Modern JVMs (G1, ZGC, Shenandoah, Parallel GC) mostly use **generational** approach:

```
Young Generation (short-lived objects)       →   Old Generation (long-lived objects)
Eden ←→ Survivor 0 ←→ Survivor 1             →   Tenured / Old Gen
     Minor GC (fast)                               Major / Full GC (expensive)
```

**Most common causes of memory leaks in Java:**

1. Static collections keeping references forever
2. Unremoved listeners / event subscribers
3. ThreadLocal variables not removed
4. Cache without eviction policy (or too weak)
5. Non-static inner classes (implicit outer reference)
6. Forgotten Closeable resources (Connection, Stream, etc.)
7. Huge object graphs kept by single long-lived object

## 5. Basic Interview Questions (with short answers)
1. **What actually is a memory leak in Java?**

    → A memory leak happens when your program keeps objects in memory that it no longer needs and the garbage collector cannot remove them because something is still holding a reference to them.

```java
import java.util.ArrayList;
import java.util.List;

public class MemoryLeakStaticCollection {

    // This list lives FOREVER because it's static
    private static final List<byte[]> permanentStorage = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 1000; i++) {
            // Create 5MB chunk
            byte[] bigChunk = new byte[5 * 1024 * 1024];

            // We "accidentally" keep reference forever
            permanentStorage.add(bigChunk);

            System.out.println("Iteration " + i + " → used memory ≈ " 
                + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024 + " MB");
            
            Thread.sleep(50); // just to watch slowly growing
        }
    }
}
```

## 6. Intermediate Interview Questions (with short answers)

1. **Can primitive types cause memory leak?**  
   → No (they don't have references)

2. **What happens when you write `String s = "hello";` twice?**  
   → Both references point to same object in **String pool** (part of heap)

3. **Difference between `System.gc()` and actual garbage collection?**  
   → `System.gc()` is **only a hint** – JVM may completely ignore it

4. **When is an object eligible for garbage collection?**  
   → When it is **no longer reachable** from any GC root  
   (stack variables, static fields, thread locals, JNI, etc.)

5. **Can two objects reference each other and still be garbage collected?**  
   → **Yes** – if there is no path from any GC root to either of them (islands of isolation are collected)

6. **What is the main disadvantage of finalizers?**  
   → Unpredictable execution time, can delay reclamation, performance overhead  
   → Almost never recommended in modern Java (use try-with-resources + AutoCloseable)

7. **WeakReference vs SoftReference vs PhantomReference – when to use each?**

   | Reference Type     | Purpose / When GC collects                             | Typical Use-case                     |
   |--------------------|--------------------------------------------------------|--------------------------------------|
   | WeakReference      | Collect as soon as no strong refs left                 | Canonical mappings, caches           |
   | SoftReference      | Collect when JVM is **low on memory** (before OOM)     | Memory-sensitive caches              |
   | PhantomReference   | Collect normally + get notification **after** collect | Resource cleanup, post-mortem action |

8. **Why can static inner classes help prevent memory leaks?**  
   → They don't hold implicit reference to outer instance

9. **How much memory does empty `new Object()` take approximately?** (HotSpot 64-bit)  
   → ~16 bytes (object header 12B + padding to 8B alignment)

10. **What is Compressed Oops and when is it used?**  
    → 32-bit pointers inside 64-bit JVM when heap < ~26–32GB  
    → Saves a lot of memory (≈30–40%)

## Quick Memory Optimization Checklist

- Prefer `int`/`long` over `Integer`/`Long` in collections when possible
- Use `StringBuilder` / `StringBuffer` in loops
- Choose right collection (ArrayList vs LinkedList vs HashSet vs EnumSet…)
- Close resources (try-with-resources)
- Remove listeners when component dies
- Use WeakHashMap / soft caches when appropriate
- Monitor with VisualVM, JMC, async-profiler, etc.

## Static keyword for memory management
Static itself doesn't save or waste memory.
What you do with the static field decides whether memory usage becomes better or catastrophically worse.

```
static field = good for memory when:
  • it's a constant               (static final String, static final int...)
  • it's a shared configuration   (static final Logger, static Cache with bounded size)
  • it's a small counter/statistic
  • it's a singleton/service

static field = very dangerous when:
  • you .add() things to it without ever removing
  • it's a Collection/Map that accumulates data during long-running application
  • threads/caches/queues are stored statically without any eviction policy
```