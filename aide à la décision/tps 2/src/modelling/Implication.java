package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Implication implements Constraint
{
    public Set<Variable> getScope()
    {
        return new HashSet<>();
    }

    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) throws IllegalArgumentException
    {
        return true;
    }
}
