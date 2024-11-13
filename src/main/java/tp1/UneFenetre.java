package tp1;

import java.awt.*;
import javax.swing.*;

class UneFenetre extends JFrame
{
    UnMobile sonMobile;
    private final int LARG=400, HAUT=100;
    private final int NBRLIG=2, NBRCOL=2;
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

//        JButton sonBouton1 = new JButton ("Start/Stop");
//        leContainer.setSize(300, 100);
//
//        sonBouton1.addActionListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                //TODO
//            }
//        });
//
//        leContainer.add(sonBouton1);
//        sonBouton1.setVisible(true);
    }
}
