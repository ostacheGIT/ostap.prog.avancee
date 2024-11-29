package tp4;

public class Assignment102 {
    public static void main(String[] args) {
        PiMonteCarlo PiVal = new PiMonteCarlo(100000);
        long startTime = System.currentTimeMillis();
        double value = PiVal.getPi();
        long stopTime = System.currentTimeMillis();
        System.out.println("Approx value:" + value);
        System.out.println("Difference to exact value of pi: " + (value - Math.PI));
        System.out.println("Error: " + (value - Math.PI) / Math.PI * 100 + " %");
        System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Time Duration: " + (stopTime - startTime) + "ms");
    }
}
