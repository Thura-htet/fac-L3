package planning;

import modelling.Variable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AStarPlanner implements Planner
{
    Map<Variable, Object> init;
    Set<Action> actions;
    Goal goal;
    Heuristic heuristic;

    public AStarPlanner( Map<Variable, Object> init, Set<Action> actions, Goal goal, Heuristic heuristic)
    {
        this.init = init;
        this.actions = actions;
        this.goal = goal;
        this.heuristic =heuristic;
    }

    public Map<Variable, Object> getInitialState() { return this.init; }

    public Set<Action> getActions() { return this.actions; }

    public Goal getGoal() { return this.goal; }

    public List<Action> plan()
    {
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Float> distances = new HashMap<>();
        Map<Map<Variable, Object>, Float> values = new HashMap<>();
        Set<Map<Variable, Object>> open = new HashSet<>();

        father.put(this.init, null);
        distances.put(init, Float.valueOf(0));
        values.put(init, heuristic.estimate(init));

        return null;
    }

}
