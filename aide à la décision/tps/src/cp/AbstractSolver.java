package cp;

import modelling.Constraint;
import modelling.Variable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.SpringLayout;

public abstract class AbstractSolver implements Solver
{
    protected Set<Variable> variables;
    protected Set<Constraint> constraints;

    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints)
    {
        this.variables = variables;
        this.constraints = constraints;
    }

    public boolean isConsistent(Map<Variable, Object> instantiations)
    {
        for (Constraint constraint : this.constraints)
        {
            if (!instantiations.keySet().containsAll(constraint.getScope()))
                continue;

            if (!constraint.isSatisfiedBy(instantiations))
                return false;
        }
        return true;
    }
}