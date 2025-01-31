package tp4;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;

public class Pi {
    public static void main(String[] args) throws Exception {
        int[] numWorkersList = {1, 2, 4, 8, 16, 32}; // Test different numbers of workers
        int totalCount = 16000000; // Total points remains constant for strong scalability

        Master master = new Master();
        master.runExperiments(numWorkersList, totalCount);
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
