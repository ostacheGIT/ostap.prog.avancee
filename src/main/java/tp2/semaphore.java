package tp2;

import java.util.concurrent.Semaphore;

public abstract class semaphore {

    protected int valeur=0;

    protected semaphore (int valeurInitiale){
        valeur = valeurInitiale>0 ? valeurInitiale:0;
    }

    /* Permet l'accès a une ressource */
    public synchronized void syncWait(){
        try {
            while(valeur<=0){
                wait();
            }
            valeur--;
        } catch(InterruptedException e){}
    }

    /* Permet la libéation de la ressource */
    public synchronized void syncSignal(){
        if(++valeur > 0) notifyAll();
    }
}