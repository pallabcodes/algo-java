package solid.dip.constructor_injection;

// One way to implement DIP is through constructor injection, where the required dependencies are provided through the constructor at runtime (when the said class is being instantiated)
interface Worker {
    void work();
}

class Developer implements Worker {
    public void work() {
        System.out.println("Writing code");
    }
}

class Manager {
    private Worker worker;

    public Manager(Worker worker) {
        this.worker = worker;
    }

    public void manage() {
        worker.work();
    }
}

public class Main {
    public static void main(String[] args) {
        Worker worker = new Developer();
        Manager manager = new Manager(worker);
        manager.manage();
    }
}
