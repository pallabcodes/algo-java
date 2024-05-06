package solid.dip.interface_injection;

interface Worker {
    public void work();
}

interface WorkerSetter {
    public void setWorker(Worker worker);
}

class Tester implements Worker {
    @Override
    public void work() {
        System.out.println("Testing software");
    }
}

class Manager implements WorkerSetter {
    private Worker worker;

    @Override
    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public void manage() {
        if (worker != null) {
            worker.work();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Worker worker = new Tester();
        Manager manager = new Manager();
        manager.setWorker(worker);
        manager.manage();
    }
}
