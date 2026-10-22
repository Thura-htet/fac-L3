package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UnaryConstraint implements Constraint
{

    private Variable variable;
    private Set<Object> sub;

    public UnaryConstraint(Variable v, Set<Object> s) 
    {
        this.variable = v;
        this.sub = s;
    }

    @Override
    public Set<Variable> getScope() 
    {
        Set<Variable> scope = new HashSet<>();
        scope.add(this.variable);
        return scope;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instantiation) 
    {

        if (!instantiation.containsKey(this.variable))
            throw new IllegalArgumentException("Variable doesn't exist.");
        return this.sub.contains(instantiation.get(this.variable));
    }

    @Override
    public String toString()
    {
        return "UnaryConstraint: [v=" + this.variable + ", s=" + this.sub + "]";
    }
}
