package tp1;

import java.awt.*;
import javax.swing.*;

class UneFenetre extends JFrame
{
    UnMobile sonMobile;
    private final int LARG=400, HAUT=100;
    private final int NBRLIG=5, NBRCOL=0;
    public UneFenetre()
    {
        super("Le mobile");
        Container leContainer = getContentPane();
        leContainer.setLayout (new GridLayout(NBRLIG, NBRCOL));

        for (int i = 1; i <= NBRLIG; i++){
            for (int j = 0; j <= NBRCOL; j++) {
                sonMobile = new UnMobile(LARG, HAUT);
                leContainer.add(sonMobile);
                Thread laTache = new Thread(sonMobile);
                laTache.start();
            }
            setSize(LARG, NBRLIG * HAUT);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }
    }
}
