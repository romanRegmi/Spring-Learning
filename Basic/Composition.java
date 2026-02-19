// A complete example of a class demonstrating composition in Java
class Engine {
    public void start() {
        System.out.println("Engine started.");
    }
}
class Car {
    // Car has an Engine (composition)
    private Engine engine;

    public Car() {
        this.engine = new Engine(); // Car creates its own Engine
    }

    public void startCar() {
        engine.start(); // Delegating the start action to the Engine
        System.out.println("Car started.");
    }
}

// Implement the same thing without composition using inheritance
class Vehicle {
    public void start() {
        System.out.println("Vehicle started.");
    }
}
class InheritedCar extends Vehicle {
    // InheritedCar does not have its own Engine, it uses Vehicle's start method
    public void startCar() {
        start(); // Calls Vehicle's start method
        System.out.println("Inherited Car started.");
    }
}

/*
* How is composition better than inheritance?
* Composition is often preferred over inheritance because it provides greater flexibility and allows for better separation of concerns. With composition, you can change the behavior of a class at runtime by composing it with different objects, whereas inheritance creates a tight coupling between the parent and child classes. Additionally, composition helps to avoid the problems associated with deep inheritance hierarchies, such as the fragile base class problem.
*/


// Usage
public class Composition {
    public static void main(String[] args) {
        Car myCar = new Car();
        myCar.startCar();
    }
}