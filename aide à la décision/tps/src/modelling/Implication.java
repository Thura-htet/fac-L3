package modelling;

import java.util.*;

// Contrainte (variable1 dans valeurs1) -> (variable2 dans valeurs2)
public class Implication implements Constraint {

    private Variable variable1;
    private Set<Object> valeurs1;
    private Variable variable2;
    private Set<Object> valeurs2;

    public Implication(Variable variable1, Set<Object> valeurs1, Variable variable2, Set<Object> valeurs2)
    {
        this.variable1 = variable1;
        this.valeurs1 = valeurs1;
        this.variable2 = variable2;
        this.valeurs2 = valeurs2;
    }

    @Override
    public Set<Variable> getScope()
    {
        Set<Variable> scope = new HashSet<>();
        scope.add(this.variable1);
        scope.add(this.variable2);
        return scope;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instantiation)
    {

        for (Variable v : this.getScope()) {
            if (!instantiation.containsKey(v)) {
                throw new IllegalArgumentException("variable sans valeur dans l'instanciation");
            }
        }

        // si la condition est vraie on verifie la conclusion
        if (this.valeurs1.contains(instantiation.get(this.variable1))) {
            return this.valeurs2.contains(instantiation.get(this.variable2));
        }

        // sinon l'implication est vraie
        return true;
    }

    @Override
    public String toString()
    {
        return "Implication [variable1=" + this.variable1 + ", valeurs1=" + this.valeurs1
                + ", variable2=" + this.variable2 + ", valeurs2=" + this.valeurs2 + "]";
    }
}
