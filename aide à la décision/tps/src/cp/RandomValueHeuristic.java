package cp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import modelling.Variable;


/* 
Exercice 11. Écrire une interface nommée ValueHeuristic, déclarant une méthode ordering qui prend
en arguments, dans cet ordre, une variable (de type Variable) et son domaine (de type Set<Object>),
et qui retourne une liste contenant les valeurs du domaine, ordonnées selon l’heuristique (la meilleure en
premier).
Exercice 14. Écrire une classe nommée RandomValueHeuristic, implémentant l’interface ValueHeu-
ristic, dont le constructeur prend en argument un générateur aléatoire (de type Random), et dont la
méthode ordering retourne une liste mélangée (pseudo-)uniformément. */


public class RandomValueHeuristic implements ValueHeuristic
{
    private Random random;

    public RandomValueHeuristic(Random random) 
    {
        this.random = random;
    }

    @Override
    public List<Object> ordering(Variable variable, Set<Object> domain)
    {
        List<Object> res = new ArrayList<>(domain);
        Collections.shuffle(res, random);
        return res;
    }
}