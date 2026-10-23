package cp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;



/* Exercice 15. Écrire une classe nommée HeuristicMACSolver, étendant la classe AbstractSolver,
et dont le constructeur prend en arguments, dans cet ordre, un ensemble de variables, un ensemble de
contraintes, une heuristique sur les variables, et une heuristique sur les valeurs. */

public class HeuristicMACSolver extends AbstractSolver
{
    private VariableHeuristic variableHeuristic;
    private ValueHeuristic valueHeuristic;

    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic)
    {
        super(variables, constraints);
        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;
    }

    @Override
    public Map<Variable, Object> solve()
    {
        if (this.variables == null)
            return null;

        Map<Variable, Set<Object>> domainMap = new HashMap<>();
        for (Variable variable : this.variables)
            domainMap.put(variable, new HashSet<>(variable.getDomain()));

        return HeuristicMAC(new HashMap<>(), new HashSet<>(this.variables), domainMap);
    }

    public Map<Variable, Object> HeuristicMAC(Map<Variable, Object> partInstantiation, Set<Variable> open, Map<Variable, Set<Object>> domainMap)
    {
        if (open.isEmpty())
            return partInstantiation;

        ArcConsistency arc = new ArcConsistency(this.constraints);
        if (!arc.ac1(domainMap))
            return null;

        Variable variable = variableHeuristic.best(open, domainMap);
        open.remove(variable);
        List<Object> values = valueHeuristic.ordering(variable, domainMap.get(variable));
        for (Object value : values) // differs from backtrack here since backtrack checks a variables domain
        {
            Map<Variable, Object> newInstantiation = new HashMap<>(partInstantiation);
            newInstantiation.put(variable, value);

            if (!this.isConsistent(newInstantiation))
                continue;
            Map<Variable, Object> reInstantiation = HeuristicMAC(newInstantiation, open, domainMap);
            if (reInstantiation != null)
                return reInstantiation;
        }
        open.add(variable);
        return null;
    }
}