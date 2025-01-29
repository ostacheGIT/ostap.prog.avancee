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
        while (!executor.isTerminated()) {
        }
        value = 4.0 * nAtomSuccess.get() / nThrows;
        return value;
    }
}

public class Assignment102 {
    public static void main(String[] args) {
        int[] nThreadsList = {1, 2, 4, 8, 16, 32};
        int n_tot = 16000000;

        try (FileWriter csvWriter = new FileWriter("out_ass102.txt", true)) {
            csvWriter.append("erreure relative | ntot | nb process | speedup\n");
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier CSV : " + e.getMessage());
            return;
        }

        long executionTime1Thread = 0;
        for (int nThreads : nThreadsList) {
            PiMonteCarlo piVal = new PiMonteCarlo(n_tot / nThreads);

            long startTime = System.nanoTime();
            double value = piVal.getPi(nThreads);
            long endTime = System.nanoTime();

            long executionTime = TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);
            if (nThreads == 1) {
                executionTime1Thread = executionTime;
            }

            double speedup = (double) executionTime1Thread / executionTime;
            double relativeError = Math.abs((value - Math.PI) / Math.PI);

            System.out.printf("%e | %d | %d | %.1f\n",
                    relativeError, n_tot, nThreads, speedup);

            try (FileWriter csvWriter = new FileWriter("out_ass102.txt", true)) {
                csvWriter.append(String.format("%e | %d | %d | %.1f\n",
                        relativeError, n_tot, nThreads, speedup));
            } catch (IOException e) {
                System.err.println("Erreur lors de l'écriture dans le fichier CSV : " + e.getMessage());
            }
        }
    }
}