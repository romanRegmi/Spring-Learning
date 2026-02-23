# Lambda Expression

Lamda Expression is an anynomous function

1. Doesn't have any name
2. Doesn't have a return type
3. Doesn't have a modifier

## Steps to make any function a lambda expression

1. Remove modifier
2. Remove return type
3. Remove method name
4. Place arrow

```java
private void sayHello(String fname){
    System.out.println("Hello : " + fname);
}

(fname) -> {System.out.println("Hello : " + fname);} 
```
