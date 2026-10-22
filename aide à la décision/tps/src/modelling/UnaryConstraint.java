package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UnaryConstraint implements Constraint
{

    private Variable variable;
    private Set<Object> values;

    public UnaryConstraint(Variable variable, Set<Object> values) 
    {
        this.variable = variable;
        this.values = values;
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
        return this.values.contains(instantiation.get(this.variable));
    }

    @Override
    public String toString()
    {
        return "UnaryConstraint: [v=" + this.variable + ", s=" + this.values + "]";
    }

    public Set<Object> getValues()
    {
        return this.values;
    }

    public Variable getVariable()
    {
        return this.variable;
    }
}
