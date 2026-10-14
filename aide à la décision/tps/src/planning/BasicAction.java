package planning;

import modelling.Variable;

import java.util.HashMap;
import java.util.Map;

public class BasicAction implements Action 
{
    private Map<Variable, Object> precondition, effect;
    private int cost;

    public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object> effect, int cost)
    {
        this.precondition = precondition;
        this.effect = effect;
        this.cost = cost;
    }

    public boolean isApplicable(Map<Variable, Object> state)
    {
        for (var e : this.precondition.entrySet())
        {
            if (state.containsKey(e.getKey()) && !state.get(e.getKey()).equals(e.getValue()))
                return false;
        }
        return true;
    }

    public Map<Variable, Object> successor(Map<Variable, Object> state)
    {
        if (this.isApplicable(state))
        {
            Map<Variable, Object> res = new HashMap<Variable, Object>(state);
            for (var e : this.effect.entrySet())
            {
                res.put(e.getKey(), e.getValue());
            }
            return res;
        }
        return new HashMap<>(state);
    }

    public int getCost()
    {
        return this.cost;
    }
}
