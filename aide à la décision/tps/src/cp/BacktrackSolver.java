package cp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

public class BacktrackSolver extends AbstractSolver
{

    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints)
    {
        super(variables, constraints);
    }

    @Override
    public Map<Variable, Object> solve()
    {
        if (this.variables == null)
            return null;
        List<Variable> open = new LinkedList<>(this.variables);
        return backtrack(new HashMap<>(), open);
    }

    public Map<Variable, Object> backtrack(Map<Variable, Object> partInstantiation, List<Variable> open)
    {
        if (open.isEmpty())
            return partInstantiation;

        Variable variable = open.removeLast();
        for (Object value : variable.getDomain())
        {
            Map<Variable, Object> newInstantiation = new HashMap<>(partInstantiation);
            newInstantiation.put(variable, value);

            if (!this.isConsistent(newInstantiation))
                continue;
            Map<Variable, Object> resInstatiation = backtrack(newInstantiation, open);
            if (resInstatiation != null)
                return resInstatiation;
        }
        open.add(variable);
        return null;
    }

}