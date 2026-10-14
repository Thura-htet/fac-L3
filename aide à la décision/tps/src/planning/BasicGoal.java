package planning;

import modelling.Variable;

import java.util.Map;

public class BasicGoal implements Goal 
{
    private Map<Variable, Object> instantiation;

    public boolean isSatisfiedBy(Map<Variable, Object> state)
    {
        for (var e : this.instantiation.entrySet())
        {
            if (!state.containsKey(e.getKey()) || state.get(e.getKey()) != e.getValue())
                return false;
        }
        return true;
    }
}
