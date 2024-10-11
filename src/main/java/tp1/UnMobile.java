package tp1;
import java.awt.*;
import javax.swing.*;

class UnMobile extends JPanel implements Runnable
{
    int saLargeur, saHauteur, sonDebDessin;
    final int sonPas = 90, sonTemps=20, sonCote=40;


    UnMobile(int telleLargeur, int telleHauteur)
    {
        super();
        saLargeur = telleLargeur;
        saHauteur = telleHauteur;
        setSize(telleLargeur, telleHauteur);
    }

    public void run()
    {
        while (true){
            for (sonDebDessin=0; sonDebDessin < saLargeur - sonCote; sonDebDessin+= sonPas)
            {
                repaint();
                try{Thread.sleep(sonTemps);}
                catch (InterruptedException telleExcp)
                {telleExcp.printStackTrace();}
            }
            for (sonDebDessin = saLargeur - sonCote; sonDebDessin >= 0; sonDebDessin -= sonPas){
                repaint();
                try{Thread.sleep(sonTemps);}
                catch (InterruptedException telleExcp)
                {telleExcp.printStackTrace();}
            }
        }

    }

    public void paintComponent(Graphics telCG)
    {
        super.paintComponent(telCG);
        telCG.fillRect(sonDebDessin, saHauteur/2 - sonCote / 2, sonCote, sonCote);
    }
}