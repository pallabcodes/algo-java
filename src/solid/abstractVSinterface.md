# Yes, in Java, one class can extend only one other class, whether i.e. abstract or not.

## Key Differences Between Abstract Classes and Interfaces in Java

### 1. Default Implementation

- **Abstract Classes**: Can provide a default implementation of methods.
  ```java
  public abstract class Vehicle {
      public void start() {
          System.out.println("Vehicle is starting");
      }
      public abstract void drive();
  }
  ```

## Interfaces: Before Java 8, interfaces could not provide implementations of methods. From Java 8 onwards, interfaces can provide implementations via default methods, but cannot hold state.

```java
public interface Movable {
    default void move() {
        System.out.println("Moving on the ground");
    }
}

```

### 2. State

- **Abstract Classes**: Can have fields that are inherited by subclasses.

```java
public abstract class Animal {
    protected int age;

    public abstract void makeSound();
}

```

- **Interfaces**: Cannot hold state; they can declare constants (public static final fields).

```java
public interface Constants {
    int MAX_SPEED = 120;
}

```

### 3. Multiple Inheritance

- **Abstract Classes**: Java supports single inheritance, so a class can only inherit from one
  abstract class.

```java
public abstract class Machine {
    public abstract void operate();
}
```

- **Interfaces**: A class can implement multiple interfaces, allowing for a form of multiple
  inheritance.

```java
public interface Flyable {
    void fly();
}

public interface Diveable {
    void dive();
}

public class SeaPlane implements Flyable, Diveable {
    public void fly() {
        System.out.println("Flying");
    }

    public void dive() {
        System.out.println("Diving");
    }
}

```

### 4. Constructor

- **Abstract Classes**: can have its own constructors


```java
public abstract class AbstractDevice {
    private String deviceName;
    public AbstractDevice(String name) {
        this.deviceName = name;
    }
}
```


- **Interfaces**: cannot have its own constructors

### 5. Access Modifiers (Private, Protected and Public)  

- **Abstract Classes**: Methods and fields can have any access modifiers (private, protected, public).


```java
public abstract class Person {
    private String name;
    protected void setName(String name) {
        this.name = name;
    }
}
```

- **Interfaces**: By default, all `fields` (i.e. properties) public, static and final and all `methods` are public (for Java <= 9) but now Java 9 introduced private methods in interfaces.

```java
public interface Information {
    String INFO = "Initial Info"; // public, static, and final
    default void displayInfo() {
        printInfo(); // Uses a private method in an interface (Java 9+)
    }
    private void printInfo() {
        System.out.println(INFO);
    }
}
```