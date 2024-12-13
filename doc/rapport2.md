
### speedup

tc temps calcul theorique different a chaque fois

Temps d'exec :
T1 = ntot * ti
T2 = ntot/2 * ti + (tc2)
Tp = ntot/p * ti + (tcp)
Sp = T1/Tp  = (ntot*ti) / (ntot/p * ti) + (tc)

c'est scalabilité forte (voir cahier) zone negative veut dire je ralenti

Tp > T1
Sp < 1
ntot/2 + 3/4 * ntot

(si on met dans le petit zone 1/4 donc remplace 3/4 par 1/4 alors Tp<T1 Sp > 1)

### Consignes tp : 

1) Executer et passer les parametres à l'éxécutable :
- assignment102
- PI
2) Avoir les mêmes sorties pour les 2 codes.
- err relative |PI - pi| / pi
- ntot, nb processus, tps            + printf("err ntot np tps", append);
3) Speedup "grossier" 