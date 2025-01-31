package tp4;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Master {
    public void runExperiments(int[] numWorkersList, int totalCount) throws Exception {
        // Create TXT file with header if it does not exist
        try (FileWriter txtWriter = new FileWriter("pi_scalability.txt", true)) {
            if (txtWriter.toString().isEmpty()) {
                txtWriter.write("Workers\tTime (ms)\tSpeedup\tPi\tRelative Error (%)\tTotal Points\n");
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
            return;
        }

        // Baseline measurement with 1 worker
        long startTime1Worker = System.nanoTime();
        long totalPoints = doRun(totalCount, 1);
        long endTime1Worker = System.nanoTime();
        long executionTime1Worker = TimeUnit.MILLISECONDS.convert(endTime1Worker - startTime1Worker, TimeUnit.NANOSECONDS);

        // Run experiments for different worker counts
        for (int numWorkers : numWorkersList) {
            long startTime = System.nanoTime();
            long hits = doRun(totalCount, numWorkers);
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

            // Append results to TXT
            try (FileWriter txtWriter = new FileWriter("pi_scalability.txt", true)) {
                txtWriter.write(String.format("%d\t%d\t%.2f\t%.10f\t%s\t%d\n",
                        numWorkers, executionTime, speedup, pi, formattedRelativeError, totalCount));
            } catch (IOException e) {
                System.err.println("Error writing to TXT: " + e.getMessage());
            }
        }
    }

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

    public void appendResultsToFile(int numWorkers, long executionTime, double speedup, double pi, double relativeError, long totalCount) {
        DecimalFormat df = new DecimalFormat("0.000000000000000E0");
        String formattedRelativeError = df.format(relativeError);

        try (FileWriter txtWriter = new FileWriter("pi_scalability.txt", true)) {
            txtWriter.write(String.format("%d\t%d\t%.2f\t%.10f\t%s\t%d\n",
                    numWorkers, executionTime, speedup, pi, formattedRelativeError, totalCount));
        } catch (IOException e) {
            System.err.println("Error writing to TXT: " + e.getMessage());
        }
    }
}