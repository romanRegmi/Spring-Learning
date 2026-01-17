# Exceptions in Java: Try, Catch, Finally, Throw, Throws, try-with-resources & Custom Exception

Exception Handling is a way to handle  runtime errors so that normal flow of an applicaiton can be maintained.

Checked Exception: These exceptions are checked at compile time, forcing the programmer to handle them explicitly.

Unchecked Exception: These exceptions are checked at runtime and do not require explicit handling at compile time.

## Stack Trace
```java
public class RunMain {
    public static void main(String[] args) {
		try{
            someMethod(); // This method calls another method that calls another method.
        } catch (Exception e){
            StackTraceElement[] stackTrace = e.getStackTrace();
            for(int i=0; i< stackTrace.length; i++){
                System.out.println(stackTrace[i]);
            }
        }
	}
}
```

## Throws
Instead of using try-catch, we can sometimes just throw the exception from the method

```java
public class RunMain {
    public static void main(String[] args) throws FileNotFoundException {
		callMethodOne();
		System.out.println("Application Started");
	}

	public static void callMethodOne() throws FileNotFoundException {
		System.out.println("Method One Called");
		methodOne(); // Since methodOne throws FileNotFoundException, it must be declared or handled in this method

	}

	public static void methodOne() throws FileNotFoundException {
		FileReader file = new FileReader("file.txt");
	}
}
```

## Throw
```java
public class RunMain {
	public static void main(String[] args) throws FileNotFoundException {
		callMethodOne();
		System.out.println("Application Started");
	}

	public static void callMethodOne() throws FileNotFoundException {
		System.out.println("Method One Called");
		methodOne(); // Since methodOne throws FileNotFoundException, it must be declared or handled here

	}

	public static void methodOne() throws FileNotFoundException {
		// Throw keyword used to explicitly throw an exception
		throw new FileNotFoundException("File not found in methodOne");
	}
}
```

## Try-With-Resources
```java
public class RunMain {
	// Basic example of try-with resources
	public static void main(String[] args) {
        // The file reader is auto-closed
        // If we create an object of a class that implements AutoCloseable interface, then such classes will be auto-closed when we pass it as a parameter in the try statement.
        // Example : BufferReader, FileReader etc.
		try (FileReader fr = new FileReader(new File("non_existent_file.txt"))) { 
			int data = fr.read();
			while (data != -1) {
				System.out.print((char) data);
				data = fr.read();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
		}
	}
}
```

## Custom Exception
```java
public class BankAccount {
    private double amount;

    public BankAccount(double amount){
        this.amount = amount;
    }

    public class InsufficientFundsException extends Exception {
        public InsufficientFundsException(double amount) {
            super("What do you want? You don't have money");
            this.amount = amount;
        }

        public double getAmount(){
            return amount;
        }
    }

    public void withdraw(double amount){
        if(this.amount > amount){
            throw new Exception("Insufficient balance"); // Throwing normal exception
        }
        this.amount -= amount;
    }

    public void withdraw0(double amount){
        if(this.amount > amount){
            throw new InsufficientFundsException(amount); // Throwing created exception
        }
        this.amount -= amount;
    }


}



public class RunMain {
	public static void main(String[] args) {
		BankAccount bankAccount = new BankAccount(10);
        try {
            bankAccount.withdraw(11);
        } catch (Exception e) {
            System.out.println(e); // java.lang.Exception: Insufficient balance
        }

        try {
            bankAccount.withdraw0(11);
        } catch (Exception e) {
            System.out.println(e); // InsufficientFundsException: What do you want?...
        }

        try {
            bankAccount.withdraw0(11);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getAmount()); 
        }
	}
}
```

