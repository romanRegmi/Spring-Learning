# JDK, JVM, JRE, javac, Byte code, JIT

┌───────────────────────────────────────────────────────┐
│                                                       │
│           ┌──────────────────────────┐                │
│           │           JDK            │                │
│           │  (Java Development Kit)  │                │
│           │  - Tools and libraries   │                │
│           │                          │                │
│           │                          │                │
│           │     ┌────────────────────┐                │
│           │     │       JRE          │                │
│           │     │ (Java Runtime      │                │
│           │     │  Environment)      │                │
│           │     │                    │                │
│           │     │                    │                │
│           │     │                    │                │
│           │     │   ┌─────────────┐  │                │
│           │     │   │    JVM      │  │                │
│           │     │   │(Java Virtual│  │                │
│           │     │   │   Machine)  │  │                │
│           │     │   └─────────────┘  │                │
│           │     └────────────────────┘                │
│           └───────────────────────────────────────────┘
│                                                       │
└───────────────────────────────────────────────────────┘


A `.java` file is compiled using javac to create a Bytecode that is platform independent. JVM interpretates this Bytecode and turns it into a machine code that is platform dependent. 

JIT : Just In Time Compiler. Inside JVM, there is a JIT. A block that is used multiple times is compiled by this.