package planning;

import modelling.Variable;

import java.util.Map;

public class BasicGoal implements Goal 
{
    private Map<Variable, Object> instantiation;

    public BasicGoal(Map<Variable, Object> instantiation)
    {
        this.instantiation = instantiation;
    }

    public boolean isSatisfiedBy(Map<Variable, Object> state)
    {
        for (Map.Entry<Variable, Object> e : this.instantiation.entrySet())
        {
            if (!state.containsKey(e.getKey()) || !state.get(e.getKey()).equals(e.getValue()))
                return false;
        }
        return true;
    }
}
