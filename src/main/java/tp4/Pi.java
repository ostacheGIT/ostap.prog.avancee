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

        // Create CSV file with header
        try (FileWriter csvWriter = new FileWriter("pi_scalability.csv")) {
            csvWriter.append("Workers,Time (ms),Speedup,Pi,Relative Error (%),Total Points\n");
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
            return;
        }

        // Baseline measurement with 1 worker
        long startTime1Worker = System.nanoTime();
        long totalPoints = new Master().doRun(totalCount, 1);
        long endTime1Worker = System.nanoTime();
        long executionTime1Worker = TimeUnit.MILLISECONDS.convert(endTime1Worker - startTime1Worker, TimeUnit.NANOSECONDS);

        // Run experiments for different worker counts
        for (int numWorkers : numWorkersList) {
            long startTime = System.nanoTime();
            long hits = new Master().doRun(totalCount, numWorkers);
            long endTime = System.nanoTime();
            long executionTime = TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);

            // Calculate metrics
            double pi = 4.0 * hits / totalCount;
            double relativeError = Math.abs((pi - Math.PI) / Math.PI) * 100;
            double speedup = (double) executionTime1Worker / executionTime;

            // Format relative error
            DecimalFormat df = new DecimalFormat("0.000000000000000E0");
            String formattedRelativeError = df.format(relativeError);

            System.out.printf("Workers: %2d  Time: %5d ms  Speedup: %5.2f  Pi: %.6f  Error: %s\n",
                    numWorkers, executionTime, speedup, pi, formattedRelativeError);

            // Append results to CSV
            try (FileWriter csvWriter = new FileWriter("pi_scalability.csv", true)) {
                csvWriter.append(String.format("%d,%d,%.2f,%.10f,%s,%d\n",
                        numWorkers, executionTime, speedup, pi, formattedRelativeError, totalCount));
            } catch (IOException e) {
                System.err.println("Error writing to CSV: " + e.getMessage());
            }
        }
    }
}

class Master {
    public long doRun(int totalCount, int numWorkers) throws InterruptedException, ExecutionException {
        List<Callable<Long>> tasks = new ArrayList<>();
        int baseTasks = totalCount / numWorkers;
        int extraTasks = totalCount % numWorkers;

        // Create tasks with proper work distribution
        for (int i = 0; i < numWorkers; i++) {
            int taskSize = baseTasks + (i < extraTasks ? 1 : 0);
            tasks.add(new Worker(taskSize));
        }

        ExecutorService exec = Executors.newFixedThreadPool(numWorkers);
        List<Future<Long>> results = exec.invokeAll(tasks);

        // Sum results from all workers
        long totalHits = 0;
        for (Future<Long> f : results) {
            totalHits += f.get();
        }

        exec.shutdown();
        return totalHits;
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
