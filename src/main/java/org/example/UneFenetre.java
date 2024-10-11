package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class UneFenetre extends JFrame
{
    UnMobile sonMobile;
    private final int LARG=2000, HAUT=200;
    private final int NBRLIG=5, NBRCOL=0;

    public UneFenetre()
    {
        // TODO
        // ajouter sonMobile a la fenetre
        // creer une thread laThread avec sonMobile
        // afficher la fenetre
        // lancer laThread
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
