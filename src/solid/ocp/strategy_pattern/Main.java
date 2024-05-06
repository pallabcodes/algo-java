package solid.ocp.strategy_pattern;

interface SortStrategy {
    void sort(int[] array);
}

class BubbleSort implements SortStrategy {

    @Override
    public void sort(int[] array) {
        System.out.println("Sorting using bubble sort");
        // Implementation of bubble sort
    }
}

class QuickSort implements SortStrategy {

    @Override
    public void sort(int[] array) {
        System.out.println("Sorting using quick sort");
        // Implementation of quick sort
    }
}

class Sorter {
    private SortStrategy sortStrategy;

    public Sorter(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public void setSortStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public void sortArray(int[] array) {
        sortStrategy.sort(array);
    }
}

public class Main {
    public static void main(String[] args) {
        int[] array = {5, 3, 8, 4};
        Sorter sorter = new Sorter(new BubbleSort());
        sorter.sortArray(array);

        sorter.setSortStrategy(new QuickSort());
        sorter.sortArray(array);
    }
}
