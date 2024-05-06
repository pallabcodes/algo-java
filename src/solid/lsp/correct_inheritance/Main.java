package solid.lsp.correct_inheritance;

class Bird {
    public void fly() {
        System.out.println("This bird can fly.");
    }
}

class Duck extends Bird {
    @Override
    public void fly() {
        System.out.println("Duck flies over the lake.");
    }
}

class Ostrich extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Ostrich cannot fly");
    }
}

public class Main {
    public static void testBirdFlying(Bird bird) {
        bird.fly();
    }

    public static void main(String[] args) {
        Bird myDuck = new Duck();
        testBirdFlying(myDuck);  // Works fine

        Bird myOstrich = new Ostrich();
        testBirdFlying(myOstrich);  // Throws exception, violates LSP
    }
}

/*
* interface FlyingBird {
    void fly();
}

class Bird {
    // Base class properties and methods common to all birds
}

class Duck extends Bird implements FlyingBird {
    public void fly() {
        System.out.println("Duck flies over the lake.");
    }
}

class Ostrich extends Bird {
    // Ostrich does not implement FlyingBird
}

public class Main {
    public static void testFlyingBird(FlyingBird bird) {
        bird.fly();
    }

    public static void main(String[] args) {
        FlyingBird myDuck = new Duck();
        testFlyingBird(myDuck);  // Works fine

        // Bird myOstrich = new Ostrich();  // Does not implement FlyingBird, thus not passed to testFlyingBird
    }
}

*
*
*
* */