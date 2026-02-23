# Functional Interface

Interface having exactly single abstract method but can have any number of defaults and static methods. We can invoke lambda expression by using functional interface.

Basically, annotating and interface with @FunctionalInterface will make the interface throw error if more than one abstract method is defined in the interface. However, it is not compulsory to use this annotation. If an interface has only one abstract method, the interface is a functional interface. The annotation only helps enforce it. 

```java
@FunctionalInterface
public interface MyInterface {
    public void sayHello();

    public void sayHi(); // Error will be thrown.
}
```

## Inheritance in Functional Interface

```java
public interface Parent { // This is a functional interface as the interface only has one abstract method
    public void sayHello();
}

@FunctionalInterface
public interface Child extends Parent { // This is a functional interface
}

@FunctionalInterface
public interface Child extends Parent {
    public void sayBye(); // Adding this will throw an error.
}

@FunctionalInterface
public interface Child extends Parent {
    public void sayHello(); // This will not throw and error as this is the same method as in Parent.
```

## Default Methods in Interface

We can have concrete methods inside interface

```java
interface A {
    default void sayHello(){
        System.out.println("Hello");
    }
}
```