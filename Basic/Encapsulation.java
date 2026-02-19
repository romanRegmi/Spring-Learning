
/*
* What is the point of encapsulation if we are eventually going to create getters and setters for everything?
* The point of encapsulation is to control access to the internal state of an object. By using getters and setters, we can add validation, logging, or other logic when accessing or modifying fields.
* In the example below, the setter for age includes a simple validation to prevent negative ages.
*/
class Person {
    // private fields
    private String name;
    private int age;

    // public getter for name
    public String getName() {
        return name;
    }

    // public setter for name
    public void setName(String name) {
        this.name = name;
    }

    // public getter for age
    public int getAge() {
        return age;
    }

    // public setter for age
    public void setAge(int age) {
        if (age >= 0) { // simple validation
            this.age = age;
        }
    }
}

// Same class without encapsulation
class PersonWithoutEncapsulation {
    // public fields
    public String name;
    public int age;
}
