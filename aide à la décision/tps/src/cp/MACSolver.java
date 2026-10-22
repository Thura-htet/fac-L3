package cp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

import modelling.Constraint;
import modelling.Variable;

public class MACSolver extends AbstractSolver
{
    public MACSolver(Set<Variable> variables, Set<Constraint> constraints)
    {
        super(variables, constraints);
    }

    public Map<Variable, Object> solve()
    {
        if (this.variables == null)
            return null;

        List<Variable> open = new LinkedList<>(this.variables);
        Map<Variable, Set<Object>> domainMap = new HashMap<>();
        for (Variable variable : this.variables)
            domainMap.put(variable, new HashSet<>(variable.getDomain()));

        return MAC(new HashMap<>(), open, domainMap);
    }

    public Map<Variable, Object> MAC(Map<Variable, Object> partInstantiation, List<Variable> open, Map<Variable, Set<Object>> domainMap)
    {
        if (open.isEmpty())
            return partInstantiation;
        
        ArcConsistency arc = new ArcConsistency(this.constraints);
        if (!arc.ac1(domainMap))
            return null;

        Variable variable = open.removeLast();
        for (Object value : domainMap.get(variable)) // differs from backtrack here since backtrack checks a variables domain
        {
            Map<Variable, Object> newInstantiation = new HashMap<>(partInstantiation);
            newInstantiation.put(variable, value);

            if (!this.isConsistent(newInstantiation))
                continue;
            Map<Variable, Object> reInstantiation = MAC(newInstantiation, open, domainMap);
            if (reInstantiation != null)
                return reInstantiation;
        }
        open.add(variable);
        return null;
    }
}