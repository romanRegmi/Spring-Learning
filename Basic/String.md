## String
A string is a class in java.

```java
public class Main {
    public static void main(String[] args) {
        int t = 1; // t stores the data 1.
        String a = new String("Ram"); // the reference variable a stores the address of the object. 
        String b = new String("Ram");

        System.out.println(a==b); // False
        String c = "Shyam";
        String d = "Shyam";
        System.out.println(c==d); // True

    }
}