package org.example;

import java.awt.*;
import javax.swing.*;

class UneFenetre extends JFrame
{
    UnMobile sonMobile;
    private final int LARG=800, HAUT=300;

    public UneFenetre()
    {
        // TODO
        // ajouter sonMobile a la fenetre
        // creer une thread laThread avec sonMobile
        // afficher la fenetre
        // lancer laThread
        super("Le mobile");
        Container leContainer = getContentPane();
        sonMobile = new UnMobile(LARG, HAUT);

        leContainer.add(sonMobile);
        Thread laTache = new Thread(sonMobile);
        laTache.start();
        setSize(LARG, HAUT);
        setVisible(true);

        JButton sonBouton1= new JButton ("Start/Stop");
        leContainer.setSize(300, 100);


        leContainer.add(sonBouton1);
        sonBouton1.setVisible(true);
    }
}
