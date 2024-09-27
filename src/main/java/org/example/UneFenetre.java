package org.example;

import java.awt.*;
import javax.swing.*;

class UneFenetre extends JFrame
{
    UnMobile sonMobile;
    private final int LARG=300, HAUT=400;

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

    }
}
