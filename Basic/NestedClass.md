# Inner Classes in Java: Static, Member, Local, and Anonymous Types


## Member class
```java
public class Car {

    // Outer class properties can be accessed from inner class methods
    private String model;
    private boolean isEngineOn;

    public Car(String model){
        this.model = model;
        this.isEngineOn = false;
    }

    // Inner Member Class
    class Engine{
        void start(){
            if(!isEngineOn){
              System.out.println(model + " engine started");  
            }
        }

        void stop(){
            if(isEngineOn){
               System.out.println((model + " engine stopped");  
            }        
        }
    }
}

public class RunMain {
    public static void main(String[] args) {
        Car car = new Car("Tata Safari");
        // New engine created using an instance of the outer class
        car.Engine engine = car.new Engine();
        engine.start();
        engine.stop();
    }
}
```

## Static Nested Class

Static nested class belongs to the outerclass rather than its instance.

```java
public class Outer {

    private int outerData = 42;

    // Non-static (regular inner class) → keeps reference to Outer
    class Inner {
        void print() {
            System.out.println(outerData);   // <-- implicit reference to Outer!
        }
    }

    // Static nested class → no reference to Outer
    static class StaticNested {
        void print() {
            // System.out.println(outerData);  // ← compile error - no access!
        }
    }
}

public class Computer {
    private String brand;

    private String model;
    private OperatingSystem os;

    // This saves memory as it is attached to the class -> When an instance of Computer created, an instance of OS is also created.
    // Helps group to Computer -> A computer can have multiple USB ports.
    static class USB {
        private String type;

        public USB(String type) {
            this.type = type;
        }

        public void displayInfo() {
            System.out.println("USB Type: " + type);
        }
    }

    public Computer(String brand, String model, String osName) {
        this.brand = brand;
        this.model = model;
        this.os = new OperatingSystem(osName);
    }

    // This is used to invoke the displayInfo() in the OperatingSystem Class
    public OperatingSystem getOS() {
        return os;
    }

    // Operating system is a part of the computer - Can be made an inner member class
    class OperatingSystem {
        private String osName;

        public OperatingSystem(String osName) {
            this.osName = osName;
        }

        public void displayInfo() {
            this.osName = osName;
        }
    }

}

public class RunMain {
    public static void main(String[] args) {
        Computer computer = new Computer("HP", "2015", "Windows-7");
        computer.getOS().displayInfo();

        Computer.USB c = new Computer.USB("TYPE-C");
        Computer.USB oth = new Computer.USB("TYPE-OTHER");
    }
}
```

```text
Situation                                    Memory impact of using static
──────────────────────────────────────────── ────────────────────────────────────────
Very many small inner class instances        ★★★★★ Very significant savings
Cache entries that are inner classes         ★★★★ Big (especially + GC benefit)
Constants / shared configuration             ★★★★ Very big if many instances
Utility methods only                         ★☆☆☆ Almost zero (code is shared anyway)
Normal business logic classes                ★☆☆☆ Usually negligible


Use static nested class when:
✓ The nested class doesn't need to access outer instance fields/methods
✓ You create many instances of the nested class
✓ The nested class lives longer than the outer instance
✓ You're building caches, builders, DTOs, event objects, comparators, etc.

Use regular (non-static) inner class when:
✓ You really need access to outer instance state
✓ You create very few instances anyway
```

## Anonymous Class
When we need to implement an interface without creating its seperate implementation class. 

```java
public class ShoppingCart {
    private double totalAmount;

    public ShoppingCart(double totalAmount){
        this.totalAmount = totalAmount;
    }

    public void processPayment(Payment paymentMethod){
        paymentMethod.pay(totalAmount);
    }
}

// Cannot create instances of interface
public interface Payment {
    void pay(double amount);
}

public class RunMain {
    public static void main(String[] args) {
        ShoppingCart shoppingCart = new ShoppingCart(150);
        shoppingCart.processPayment(new Payment() {
            @Override
            public void pay(double amount){
                // Logic
            }
        });

    }
}
```

## Local Inner class

Class inside a method. The class method can access enclosing class fields. The scope will be destroyed and the class cannot be accessed outside.

```java
public class Hotel {
    private String name;
    private int totalRooms;
    private int bookedRooms;

    public Hotel(String name, int totalRooms, int bookedRooms) {
        this.name = name;
        this.totalRooms = totalRooms;
        this.bookedRooms = bookedRooms;
    }

    public void bookedRooms(String guestName, int rooms) {
        class ReservationValidator {
            boolean validate() {
                if(guestName == null || guestName.isEmpty() || rooms <= 0) {
                    return false;
                }
                if(bookedRooms + rooms > totalRooms) {
                    return false;
                }
                return true;
            }
        }

        ReservationValidator validator = new ReservationValidator();
        if(validator.validate()) {
            bookedRooms += rooms;
            System.out.println("Reservation successful for " + guestName + " for " + rooms + " rooms.");
        } else {
            System.out.println("Reservation failed for " + guestName + ".");
        }
    }
}

public class RunMain {
    public static void main(String[] args) {
		Hotel hotel = new Hotel("Grand Plaza", 100, 0);
		hotel.bookedRooms("Alice", 2);
		hotel.bookedRooms("Bob", 0); // Invalid reservation
		hotel.bookedRooms("Charlie", 99); // Exceeds available rooms
	}
}
```

