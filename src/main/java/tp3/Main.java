package tp3;

public class Main {
    public static void main(String[] args) {
        BoiteALettre boite = new BoiteALettre();
        Producteur p1 = new Producteur();
        Consommateur c1 = new Consommateur();
        p1.bufferLettre = boite;
        c1.bufferLettre = boite;
        p1.start();
        c1.start();
    }
}
