package planning;

import modelling.Variable;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class BFSPlanner implements Planner
{
    private Map<Variable, Object> init;
    private Set<Action> actions;
    private Goal goal;

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
                            return backtrack(father, plan, next);
                        open.add(next);
                    }
                }
            }
        }
        return null;
    }

    // doesn't this assume that plan is null
    public List<Action> backtrack(
        Map<Map<Variable, Object>, Map<Variable, Object>> father, 
        Map<Map<Variable, Object>, Action> plan, 
        Map<Variable, Object> goal)
    {
        List<Action> res = new ArrayList<>();
        while (father.get(goal) != null)
        {
            res.add(plan.get(goal));
            goal = father.get(goal);
        }
        return res.reversed();
    }

}
