## INTRO

Cours + Méthode code séquentiel vers un code en parallèle

Un paradigme def : voir rapport.md

Aspects : SIMD ou MIMD (voir cours)

### COURS + M2THODE

(2 classes distingués Parallélisme de tâche & données)

Méthode parallelisation :
- Analyse de l'algo (but : trouver des taches donc boucles, parties du codes à parrallèliser).

Parallélisme de tâche :
- Decoupe tâche en plusieurs sous-tâches
- Identifier les variables partagées entre les (sous-)tâches 
- Associer tâche à un processus (support d’exéc)

(cas d'utilisation = tache car scénario)

REMARQUE CODE :
chaque code = 2 phases principales : 
- phase de calcul
- phase d'échange de données

+ code de steve kaultz à voir

### Assignement 102

API concurrent où n_cible <=> nAtomSuccess

tache monte carlo = une iteration        
(ici : chaque tache MC => thread associé)
(ici : algo avec vole de tache car + adapté)
Algo des iterations parallèle

## PI

Dans le code de PI : 
- pas de biblio math : arraylist er random (util)
- 
- Classe pi :
    - Contient une méthode main.
    - main instancie un obj Master (classe) et exécute  doRun.
- Classe Master :
    - de la méthode main de pi.
    - méthode principale : doRun.
- Responsabilités de doRun :
    - calcule un total (total count).
    - gére le nombre de workers (numWorkers).
    - enregistre le temps de début de l'exécution (start time).
    - Crée une liste de tâches (tasks).
    - Chaque tâche = un worker.
- Les Workers :
    - chaq worker = un callable.
    - callable implémente Runnable.
    - retourne une valeur calculée.
- Résultats :
    - liste d'objets Future avec les résultats des tâches exécutées par les workers.
- Executor :
Utilise un algorithme d'exécution avancé :
Gestion du vol de tâches (Work Stealing).
Ordonnancement des tâches, etc.

## 13/12

code monte carlo + diagramme de classe

Atomic (atomic integer) : operation pour incrementer une section critiqu.
moniteur = atomic et donc objet qui protege un int avec points d'entrer (incrementAndGet).

### speedup

tc(temps calcul) theorique different a chaque fois

T1 = ntot * ti
T2 = ntot/2 * ti + (tc2)
Tp = ntot/p * ti + (tcp)
Sp = T1/Tp  = (ntot*ti) / (ntot/p * ti) + (tc)
(temps d'exec)

( scalabilité forte  zone negative = je ralentis )

Tp > T1
Sp < 1
ntot/2 + 3/4 * ntot

(si on met dans le petit zone 1/4 donc remplace 3/4 par 1/4 alors Tp<T1 Sp > 1)

### Consignes tp 13/12: 

1) Executer et passer les parametres à l'éxécutable (A102 : ntot et nb processor);
   Pi : ntot/p et p = nbworker):
      - assignment102
      - PI 
2) Avoir les mêmes sorties pour les 2 codes.
- err relative (|PI - pi|) / pi
- ntot, nb processus, tps            + printf("err ntot np tps", append); (sortie dans un fichier)
3) Speedup "grossier" (voir comportement en gros)