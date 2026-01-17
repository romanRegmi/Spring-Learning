# Generics
```java
public class Main {
    public static void main(String[] args){
        int[] arr = new int[5];
        ArrayList list = new ArrayList(); // No need to determine the size
        list.add("Hello");
        list.add(5);
        list.add(3.14);

        Object o = list.get(0);
        // Will require manual type casting
        String str = (String) list.get(0);
        Integer inte = (Integer) list.get(1);
        String inte = (String) list.get(1); // Will throw type cast exception

        
    }
}
```
Above code has 3 major issues

1. No Type safety
2. Manual casting
3. No Compile Time checking

These issues can be solved using Generics
```
// ArrayList with type
ArrayList<String> ls = new ArrayList<String>(); // Type safety
```

## Generic Types
A generic type is a class or interface that is parameterized over types. For example, a generic class can work with any type specified by the user, and that type can be enforced at compile time.

```java
public class Box {
    private Object value;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

public class Main {
    public static void main(String[] args) {
        Box box = new Box();
        box.setValue(1); // Integer is passed
        Integer a = (Integer) box.getValue(); // Will work.
        String i = (String) box.getValue(); // EXCEPTION !!!
        System.out.println(i);
    }
}
```
The syntax for a generic type is:
```java
class ClassName<T> {
    // Class body
}
```
Where T is the type parameter, which can be any valid identifier. Conventionally, single-letter names are used for type parameters, such as T for Type, E for Element, K for Key, V for Value, etc.

```java
public class Box<T> { //  one or more type parameters
//  These type parameters are placeholders that are replaced with specific types when the class is instantiated.
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}

public class Main {
    public static void main(String[] args) {
        Box<Integer> box = new Box<>();  // Box is now type-safe
        box.setValue(1);  // No issue, it's an Integer
        Integer i = box.getValue();  // No casting needed
        System.out.println(i);
    }
}
```

Here, Box<T> is a generic class. The type parameter T will be replaced with a specific type when an object of Box is created. Now, the Box class is type-safe, and we will not encounter the ClassCastException because the types are enforced at compile time.

Generics help us write more flexible and reusable code. For example, without generics, we would have to write multiple versions of the same class to handle different data types, leading to code duplication.

A generic class can have more than one type parameter.

```java
class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

public class Main {
    public static void main(String[] args) {
        Pair<String, Integer> pair = new Pair<>("Age", 30);
        System.out.println("Key: " + pair.getKey());   // Prints: Key: Age
        System.out.println("Value: " + pair.getValue()); // Prints: Value: 30
    }
}
```

## Type Parameter Naming Conventions

While you can name type parameters anything, the convention is to use single letters that describe the purpose of the type parameter:

* T: Type
* E: Element (used in collections)
* K: Key (used in maps)
* V: Value (used in maps)
* N: Number
