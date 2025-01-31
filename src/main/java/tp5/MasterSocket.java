package tp5;

import tp4.Master;

import java.io.*;
import java.net.*;

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
        int totalCount = 16000000;

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

            String message_to_send = String.valueOf(totalCount);
            String message_repeat = "y";

            // Sequential version
            long startTime = System.currentTimeMillis();
            double pi = 4.0 * totalCount / (double)totalCount;
            long stopTime = System.currentTimeMillis();
            sequentialTime = stopTime - startTime;

            Master master = new Master();

            while (message_repeat.equals("y")) {
                for (int i = 1; i <= numWorkers; i++) {
                    for (int j = 0; j < numWorkers; j++) {
                        System.out.println("Running experiment " + (j + 1) + " for " + i + " workers");
                        master.runExperiments(new int[]{i}, totalCount);
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
}