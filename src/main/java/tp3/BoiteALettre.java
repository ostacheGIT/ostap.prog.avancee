package tp3;

public class BoiteALettre {

    public String bufferLettre ;
    public boolean available ;

    public void depose(String lettre){
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        bufferLettre = lettre;
        available = true;
        notifyAll();
    }

    public String retire(){
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        available = false;
        notifyAll();
        return bufferLettre;
    }

}
