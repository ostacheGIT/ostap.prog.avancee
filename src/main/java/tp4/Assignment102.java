package tp4;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class PiMonteCarlo {
    AtomicInteger nAtomSuccess; // nb_cible
    int nThrows;
    double value; //PI

    class MonteCarlo implements Runnable {
        @Override
        public void run() {
            double x = Math.random();
            double y = Math.random();
            if (x * x + y * y <= 1)
                nAtomSuccess.incrementAndGet();
        }
    }

    public PiMonteCarlo(int i) {
        this.nAtomSuccess = new AtomicInteger(0);
        this.nThrows = i;
        this.value = 0;
    }

    public double getPi(int nThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        for (int i = 1; i <= nThrows; i++) {
            Runnable worker = new MonteCarlo();
            executor.execute(worker);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        value = 4.0 * nAtomSuccess.get() / nThrows;
        return value;
    }
}

public class Assignment102 {
    public static void main(String[] args) {
        int[] nThreadsList = {1, 2, 3, 4, 5, 6};
        int baseTotalCount = 160000000 ;
        boolean isStrongScalability = false; // Set to true for strong scalability, false for weak scalability

        for (int nThreads : nThreadsList) {
            int totalCount = isStrongScalability ? baseTotalCount : baseTotalCount * nThreads;
            for (int j = 0; j < 10; j++) {
                runExperiments(totalCount, nThreads);
            }
        }
    }

    private static void runExperiments(int totalCount, int nThreads) {
        PiMonteCarlo piVal = new PiMonteCarlo(totalCount);

        long startTime = System.nanoTime();
        double value = piVal.getPi(nThreads);
        long endTime = System.nanoTime();

        long executionTime = TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);
        double relativeError = Math.abs((value - Math.PI) / Math.PI) * 100;

        System.out.printf("Valeur approchée: %.10f\n", value);
        System.out.printf("Erreur: %e\n", relativeError);
        System.out.printf("N total: %d\n", totalCount);
        System.out.printf("Nombre process: %d\n", nThreads);
        System.out.printf("Temps d'exécution: %d ms\n", executionTime);

        try (FileWriter txtWriter = new FileWriter("pi_scalability.txt", true)) {
            txtWriter.write(String.format("%d\t%d\t%.2f\t%.10f\t%e\t%d\n",
                    nThreads, executionTime, 1.0, value, relativeError, totalCount));
        } catch (IOException e) {
            System.err.println("Error writing to TXT: " + e.getMessage());
        }
    }
}
