package cp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.UnaryConstraint;
import modelling.Variable;



public class ArcConsistency
{
    private Set<Constraint> constraints;

    public ArcConsistency(Set<Constraint> constraints) throws IllegalArgumentException 
    {
        for (Constraint constraint : constraints)
        {
            if (constraint.getScope().size() > 2)
                throw new IllegalArgumentException("Le constraint n'est ni unaire ni binaire.");
        }
        this.constraints = constraints;
    }
/* 
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domainMap)
    {
        for (Constraint constraint : this.constraints)
        {
            Set<Variable> scope = constraint.getScope();
            if (scope.size() != 1)
                continue;


            if (constraint instanceof UnaryConstraint unary)
            {
                Variable variable = unary.getVariable();
                Set<Object> allowedValues = unary.getValues();

                domainMap.get(variable).retainAll(allowedValues);

                if (domainMap.get(variable).isEmpty())
                    return false;
            }
        }
        return true;
    }
 */
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domainMap)
    {
        Map<Variable, Set<Object>> removeMap = new HashMap<>();
        for (Constraint constraint : this.constraints)
        {
            Set<Variable> scope = constraint.getScope();
            if (scope.size() != 1)
                continue;

            Variable variable = scope.iterator().next();
            Set<Object> values = domainMap.get(variable);
            if (!removeMap.containsKey(variable))
                removeMap.put(variable, new HashSet<>());

            for (Object value : values)
            {
                if (!constraint.isSatisfiedBy(Map.of(variable, value)))
                    removeMap.get(variable).add(value);
            }
        }

        boolean res = true;
        for (Map.Entry<Variable, Set<Object>> domain : domainMap.entrySet())
        {
            Variable variable = domain.getKey();

            if (removeMap.containsKey(variable))
                domainMap.get(variable).removeAll(removeMap.get(variable));

            if (domainMap.get(variable).isEmpty())
                res = false;
        }
        return res;
    }

    /* public boolean revise(Variable v1, Set<Object> d1, Variable v2, Set<Object> d2)
    {
        Set<Object> delete = new HashSet<>();
        for (Object a1 : d1)
        {
            boolean support = true;
            for (Object a2 : d2)
            {
                Map<Variable, Object> instantiation = new HashMap<>();
                instantiation.put(v1, a1);
                instantiation.put(v2, a2);

                for (Constraint constraint : this.constraints)
                {
                    if (!constraint.isSatisfiedBy(instantiation))
                    {
                        support = false;
                        break;
                    }
                }

                if (support)
                    break;
            }
            if (!support)
                delete.add(a1);
        }

        if (delete.isEmpty())
            return false;
        d1.removeAll(delete);
        return true;
    } */

    public boolean revise(Variable v1, Set<Object> d1, Variable v2, Set<Object> d2)
    {
        if (v1.equals(v2))
            return false;
        Set<Object> removeSet = new HashSet<>();
/* 
        String res = "";
        for (Constraint constraint : this.constraints)
            res += (constraint + ",   ");

        System.out.println(res);
*/
        
        for (Object val1 : d1) // set to be removed from
        {
            boolean val1Satisfiable = false;
            for (Object val2 : d2)
            {
                boolean constraintSatisfiable = true;
                for  (Constraint constraint : this.constraints)
                {
                    
                    Set<Variable> scope = constraint.getScope();

                    // check if given variables concern the constraint
                    Iterator<Variable> it = scope.iterator();
                    Variable constraintVariable1 = it.next();
                    Variable constraintVariable2 = it.next();
                    if ( !((v1.equals(constraintVariable1) && v2.equals(constraintVariable2))
                        || (v1.equals(constraintVariable2) && v2.equals(constraintVariable1))) )
                    {
                        // System.out.println("VARIABLE SKIPPED");
                        continue;
                    }

                    constraintSatisfiable = constraintSatisfiable && constraint.isSatisfiedBy(Map.of(v1, val1, v2, val2));
                }
                val1Satisfiable = constraintSatisfiable;
                if (val1Satisfiable)
                    break;
            }
            if (!val1Satisfiable)
                removeSet.add(val1);
        }
        boolean remove = !removeSet.isEmpty();
        if (remove)
            d1.removeAll(removeSet);
        return remove;
    }

    public boolean ac1(Map<Variable, Set<Object>> domainMap)
    {
        if (!enforceNodeConsistency(domainMap))
            return false;

        boolean change;
        do { 
            change = false;
            for (Variable v1 : domainMap.keySet())
            {
                for (Variable v2 : domainMap.keySet())
                {
                    if (revise(v1, domainMap.get(v1), v2, domainMap.get(v2)))
                        change = true;
                }
            }

        }
        while (change);

        for (Variable v : domainMap.keySet())
            if (domainMap.get(v).isEmpty())
                return false;
        return true;
    }
}