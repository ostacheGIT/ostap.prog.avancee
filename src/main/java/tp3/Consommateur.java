package tp3;

public class Consommateur extends Thread {

    public String lettreRetire;
    BoiteALettre bufferLettre;

    public  void run() {
        while (true) {
            lettreRetire = bufferLettre.retire();
        }
    }
}
