package tp3;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BoiteALettre {
    private BlockingQueue<Character> queue = new ArrayBlockingQueue<Character>(20);
    public boolean deposer(Character lettreAPoster) throws InterruptedException {
        return queue.offer(lettreAPoster, 200, TimeUnit.MILLISECONDS);

    }
    public Character retirer() throws InterruptedException {
        return queue.poll(200, TimeUnit.MILLISECONDS);
    }
    public int getQueueSize() {
        return queue.size();
    }
}
