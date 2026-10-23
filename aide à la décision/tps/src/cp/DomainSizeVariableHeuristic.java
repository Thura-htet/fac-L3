package cp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;


public class DomainSizeVariableHeuristic implements VariableHeuristic
{
    private record Pair<Variable, Integer>(Variable variable, Integer count) {}

    private boolean greatest;


    public DomainSizeVariableHeuristic(boolean greatest)
    {
        this.greatest = greatest;
    }

    @Override 
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domainMap)
    {
        Pair<Variable, Integer> max = new Pair<>(variables.iterator().next(), Integer.MIN_VALUE);
        Pair<Variable, Integer> min = new Pair<>(variables.iterator().next(), Integer.MAX_VALUE);

        for (Variable variable : variables)
        {
            int size = domainMap.get(variable).size();
            if (size > max.count())
                max = new Pair<>(variable, size);
            if (size < min.count())
                min = new Pair<>(variable, size);
        }
        if (greatest)
            return max.variable();
        return min.variable();
    }
}