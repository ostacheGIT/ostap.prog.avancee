package tp3;

public class Producteur extends Thread {

    public String lettreDepose;
    BoiteALettre bufferLettre;

    public void run() {
        while (true) {
            lettreDepose = "Lettre test" ;
            bufferLettre.depose(lettreDepose);
            System.out.println("Producteur " + this.getName() + " a déposé la lettre : " + lettreDepose);
        }
    }
}
