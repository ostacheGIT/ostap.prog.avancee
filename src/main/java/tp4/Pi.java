package tp4;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class Pi {
    public static void main(String[] args) throws Exception {
        int[] numWorkersList = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}; // Test different numbers of workers
        int[] totalCountsForte = {12000000, 120000000}; // Total points remains constant for strong scalability
        int totalCountsFaible = 16000000;
        boolean scalabilite_faible = true;

        if (scalabilite_faible == false) {
            // forte
            Master master = new Master();
            for (int totalCount : totalCountsForte) {
                for (int workers : numWorkersList) {
                    for (int j = 0; j < 10; j++) {
                        master.runExperiments(new int[]{workers}, totalCount);
                    }
                }
            }
        } else {
            // faible
            Master master = new Master();
            for (int workers : numWorkersList)  {
                int totalCount = totalCountsFaible * workers;
                for (int j = 0; j < 10; j++) {
                    master.runExperiments(new int[]{workers}, totalCount);
                }
            }
        }
    }
}

class Worker implements Callable<Long> {
    private final int iterations;

    public Worker(int iterations) {
        this.iterations = iterations;
    }

    @Override
    public Long call() {
        long hits = 0;
        for (int i = 0; i < iterations; i++) {
            double x = ThreadLocalRandom.current().nextDouble();
            double y = ThreadLocalRandom.current().nextDouble();
            if (x * x + y * y <= 1.0) {
                hits++;
            }
        }
        return hits;
    }
}
