package tp5;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class MasterSocket {
    static int maxServer = 8;
    static final int[] tab_port = {25545, 25546, 25547, 25548, 25549, 25550, 25551, 25552};
    static String[] tab_total_workers = new String[maxServer];
    static final String ip = "127.0.0.1";
    static BufferedReader[] reader = new BufferedReader[maxServer];
    static PrintWriter[] writer = new PrintWriter[maxServer];
    static Socket[] sockets = new Socket[maxServer];
    static double sequentialTime;

    public static void main(String[] args) throws Exception {
        int baseTotalCount = 16000000; // Base total count for weak scalability
        boolean isStrongScalability = false; // Set to true for strong scalability, false for weak scalability

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String s;

        System.out.println("#########################################");
        System.out.println("# Computation of PI by MC method        #");
        System.out.println("#########################################");

        System.out.println("\n How many workers for computing PI (< maxServer): ");
        try {
            s = bufferRead.readLine();
            int numWorkers = Integer.parseInt(s);
            System.out.println(numWorkers);

            // Create worker's socket
            for (int i = 0; i < numWorkers; i++) {
                sockets[i] = new Socket(ip, tab_port[i]);
                System.out.println("SOCKET = " + sockets[i]);

                reader[i] = new BufferedReader(new InputStreamReader(sockets[i].getInputStream()));
                writer[i] = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sockets[i].getOutputStream())), true);
            }

            String message_repeat = "y";

            // Sequential version
            long startTime = System.currentTimeMillis();
            double pi = 4.0 * baseTotalCount / (double)baseTotalCount;
            long stopTime = System.currentTimeMillis();
            sequentialTime = stopTime - startTime;

            while (message_repeat.equals("y")) {
                for (int i = 1; i <= numWorkers; i++) {
                    int totalCount = isStrongScalability ? baseTotalCount : baseTotalCount * i;
                    for (int j = 0; j < numWorkers; j++) {
                        System.out.println("Running experiment " + (j + 1) + " for " + i + " workers");
                        runExperiments(i, totalCount);
                    }
                }

                System.out.println("\n Repeat computation (y/N): ");
                try {
                    message_repeat = bufferRead.readLine();
                    System.out.println(message_repeat);
                } catch (IOException ioE) {
                    ioE.printStackTrace();
                }
            }

            for (int i = 0; i < numWorkers; i++) {
                System.out.println("END");
                writer[i].println("END");
                reader[i].close();
                writer[i].close();
                sockets[i].close();
            }
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }
    private static void runExperiments(int numWorkers, int totalCount) {
        long startTime = System.nanoTime();

        // Send totalCount to each worker
        for (int i = 0; i < numWorkers; i++) {
            writer[i].println(totalCount / numWorkers);
        }

        // Receive results from each worker
        int totalInsideCircle = 0;
        for (int i = 0; i < numWorkers; i++) {
            try {
                totalInsideCircle += Integer.parseInt(reader[i].readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();
        long executionTime = TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);
        double pi = 4.0 * totalInsideCircle / (double)totalCount;
        double relativeError = Math.abs((pi - Math.PI) / Math.PI) * 100;

        System.out.printf("Workers: %d  Time: %d ms  Pi: %.10f  Error: %e\n",
                numWorkers, executionTime, pi, relativeError);

        try (FileWriter txtWriter = new FileWriter("pi_scalability.txt", true)) {
            txtWriter.write(String.format("%d\t%d\t%.2f\t%.10f\t%e\t%d\n",
                    numWorkers, executionTime, 1.0, pi, relativeError, totalCount));
        } catch (IOException e) {
            System.err.println("Error writing to TXT: " + e.getMessage());
        }
    }
}
