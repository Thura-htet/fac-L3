package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DifferenceConstraint implements Constraint
{
    private Variable var1, var2;

    public DifferenceConstraint(Variable var1, Variable var2)
    {
        this.var1 = var1;
        this.var2 = var2;
    }

    public Set<Variable> getScope()
    {
        return new HashSet<Variable>() {{ 
            add(var1);
            add(var2);
        }};
    }

    public boolean isSatisfiedBy(Map<Variable, Object> instanciations) throws IllegalArgumentException
    {
        for(Variable var :this.getScope())
        {
            if(!instanciations.containsKey(var))
                throw new IllegalArgumentException("instance does not exist");
        }
        return (!instanciations.get(this.var1).equals(instanciations.get(this.var2)));
              
    }
}
