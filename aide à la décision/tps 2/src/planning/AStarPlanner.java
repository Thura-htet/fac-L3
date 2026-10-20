package planning;

import modelling.Variable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * AStarPlanner
 */
public class AStarPlanner implements Planner
{
    private Map<Variable, Object> init;
    private Set<Action> actions;
    private Goal goal;
    private Heuristic heuristic;
    private int clock = 0;
    private boolean start = false;

    public AStarPlanner( Map<Variable, Object> init, Set<Action> actions, Goal goal, Heuristic heuristic)
    {
        this.init = init;
        this.actions = actions;
        this.goal = goal;
        this.heuristic = heuristic;
    }

    public Map<Variable, Object> getInitialState()
    { 
        return this.init;
    }

    public Set<Action> getActions()
    { 
        return this.actions;
    }

    public Goal getGoal()
    {
        return this.goal;
    }

    public int getClock()
    {
        return this.clock;
    }

    public void startClock()
    {
        this.start = true;
    }

    public List<Action> plan()
    {
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Float> distances = new HashMap<>();
        Map<Map<Variable, Object>, Float> values = new HashMap<>();
        Set<Map<Variable, Object>> open = new HashSet<>();

        open.add(this.init);
        father.put(this.init, null);
        distances.put(this.init, Float.valueOf(0));
        values.put(this.init, this.heuristic.estimate(this.init));

        while (!open.isEmpty())
        {
            Map<Variable, Object> currentState = Helper.argmin(open, distances);

            if (this.goal.isSatisfiedBy(currentState))
                return Helper.backtrack(father, plan, currentState);

            open.remove(currentState);
            if (start)
                this.clock++;

            for (Action action : this.actions)
            {
                if (action.isApplicable(currentState))
                {
                    Map<Variable, Object> next = action.successor(currentState);
                    if (!distances.containsKey(next))
                        distances.put(next, Float.MAX_VALUE);
                    
                    Float projectedDistance = distances.get(currentState) + action.getCost();
                    if (distances.get(next) > projectedDistance)
                    {
                        distances.put(next, projectedDistance);
                        values.put(next, projectedDistance + heuristic.estimate(next));

                        father.put(next, currentState);
                        plan.put(next, action);
                        open.add(next);
                    }
                }
            }

        }
        return null;
    }
}
