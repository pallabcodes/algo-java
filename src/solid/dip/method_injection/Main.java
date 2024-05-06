package solid.dip.method_injection;

interface Worker {
    void work();
}

class Designer implements Worker {

    @Override
    public void work() {
        System.out.println("designer");
    }
}

class Manager {
    public void manage(Worker worker) {
        worker.work();
    }
}

public class Main {
    public static void main(String[] args) {
        Worker worker = new Designer();
        Manager manager = new Manager();
        manager.manage(worker);
    }
}
