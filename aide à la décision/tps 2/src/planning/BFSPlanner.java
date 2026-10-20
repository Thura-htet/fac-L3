package planning;

import modelling.Variable;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BFSPlanner implements Planner
{
    private Map<Variable, Object> init;
    private Set<Action> actions;
    private Goal goal;
    private int clock = 0;
    private boolean start = false;

    public BFSPlanner(Map<Variable, Object> init, Set<Action> actions, Goal goal)
    {
        this.init = init;
        this.actions = actions;
        this.goal = goal;
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
        Set<Map<Variable, Object>> closed = new HashSet<>();
        closed.add(init);
        List<Map<Variable, Object>> open = new ArrayList<>();
        open.add(init);

        // need to initialize father

        return BFS(father, plan, closed, open);
    }

    public List<Action> BFS(
        Map<Map<Variable, Object>, Map<Variable, Object>> father, 
        Map<Map<Variable, Object>, Action> plan, 
        Set<Map<Variable, Object>> closed,
        List<Map<Variable, Object>> open
    )
    {
        if (this.goal.isSatisfiedBy(this.init))
            return new ArrayList<>();

        while (!open.isEmpty())
        {
            Map<Variable, Object> currentState = open.remove(0);
            closed.add(currentState);
            if (start)
                this.clock++;

            for (Action action : this.actions)
            {
                if (action.isApplicable(currentState))
                {
                    Map<Variable, Object> next = action.successor(currentState);
                    if (!closed.contains(next) && !open.contains(next))
                    {
                        father.put(next, currentState);
                        plan.put(next, action);

                        if (this.goal.isSatisfiedBy(next))
                            return Helper.backtrack(father, plan, next);
                        open.add(next);
                    }
                }
            }
        }
        return null;
    }
}
