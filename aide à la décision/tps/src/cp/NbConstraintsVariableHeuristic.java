package cp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;


public class NbConstraintsVariableHeuristic implements VariableHeuristic
{
    private record Pair<Variable, Integer>(Variable variable, Integer count) {}

    private Set<Constraint> constraints;
    private boolean greatest;


    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean greatest)
    {
        this.constraints = constraints;
        this.greatest = greatest;
    }

    @Override 
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domainMap)
    {
        Pair<Variable, Integer> max = new Pair<>(variables.iterator().next(), Integer.MIN_VALUE);
        Pair<Variable, Integer> min = new Pair<>(variables.iterator().next(), Integer.MAX_VALUE);


        for (Variable variable : variables)
        {
            int count = 0;
            for (Constraint constraint : this.constraints)
                if (constraint.getScope().contains(variable))
                    count++;
            if (count > max.count())
                max = new Pair<>(variable, count);
            if (count < min.count())
                min = new Pair<>(variable, count);
        }
        if (greatest)
            return max.variable();
        return min.variable();
    }
}