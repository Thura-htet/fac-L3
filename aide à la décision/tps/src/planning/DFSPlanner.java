package planning;

import modelling.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DFSPlanner implements Planner
{
    private Map<Variable, Object> init;
    private Set<Action> actions;
    private Goal goal;

    public DFSPlanner(Map<Variable, Object> init, Set<Action> actions, Goal goal)
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

    public List<Action> plan()
    {
        List<Action> plan = new ArrayList<>();
        Set<Map<Variable, Object>> closed = new HashSet<Map<Variable,Object>>();
        closed.add(this.init);

        plan = DFS(this.init, plan, closed);

        return plan;
    }

    public List<Action> DFS(Map<Variable, Object> currentState, List<Action> plan, Set<Map<Variable, Object>> closed)
    {
        if (this.goal.isSatisfiedBy(currentState))
            return plan;

        for (Action action : this.actions)
        {
            if (action.isApplicable(currentState))
            {
                // whether or not hte action is action is applicable to state is
                // checked internally
                Map<Variable, Object> next = action.successor(init);
                if (!closed.contains(next))
                {
                    plan.add(action);
                    closed.add(next);

                    List<Action> subPlan = DFS(next, plan, closed);
                    if (subPlan != null)
                        return subPlan;
                    else
                        plan.remove(action);
                }
            }
        }
        return null;
    }
}
