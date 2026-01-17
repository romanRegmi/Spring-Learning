The static keyword in java is primarily used for memory management. It can be applied to variables, methods, blocks, and nested classes.

The main concept behind static is that it belongs to the class rather than instances of the class. 

The static method cannot use non static data member or call non-static method directly.
`this` and `super` cannot be used in static context.


# Static Block
This is different from static methods. This block gets executed when the class is loaded in the memory.

```java
public class Student {
    public static int count = 0;

    static {
        // We can include logic.
        // Database connection logic
        System.out.println("Hello");
    }
}
```