package planning;

import modelling.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class DijkstraPlanner implements Planner
{
    private  Map<Variable, Object> init;
    private Set<Action> actions;
    private Goal goal;

    public Map<Variable, Object> getInitialState() { return this.init; }

    public Set<Action> getActions() { return this.actions; }

    public Goal getGoal() { return this.goal; }

    public List<Action> plan()
    {
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Float> distances = new HashMap<>();
        Set<Map<Variable, Object>> open = new HashSet<>();

        father.put(this.init, null);
        distances.put(this.init, Float.valueOf(0));
        open.add(this.init);
        
        return Dijkstra(father, plan, distances, open);
    }

    public List<Action> Dijkstra(
        Map<Map<Variable, Object>, Map<Variable, Object>> father,
        Map<Map<Variable, Object>, Action> plan,
        Map<Map<Variable, Object>, Float> distances,
        Set<Map<Variable, Object>> open
    )
    {
        while (!open.isEmpty())
        {
            Map<Variable, Object> currentState = argmin(open, distances);
            open.remove(currentState);

            if (this.goal.isSatisfiedBy(currentState))
                return getDijkstraPlan(father, plan, currentState);

            for (Action action : this.actions)
            {
                if(action.isApplicable(currentState))
                {
                    Map<Variable, Object> next = action.successor(currentState);
                    if (!distances.containsKey(next))
                        distances.put(next, Float.MAX_VALUE);

                    Float projectedDistance = distances.get(currentState) + action.getCost();
                    if (distances.get(next) > projectedDistance)
                    {
                        distances.put(next, projectedDistance);
                        father.put(next, currentState);
                        plan.put(next, action);
                        open.add(next);
                    }
                }
            }
        }
        return null;
    }

    public Map<Variable, Object> argmin(Set<Map<Variable, Object>> open, Map<Map<Variable, Object>, Float> distance)
    {
        Map<Variable, Object> bestNode = new HashMap<>();
        Float bestRoute = Float.MAX_VALUE;

        for(Map<Variable, Object> o : open)
        {
            Float currentRoute = distance.get(o);
            if (currentRoute < bestRoute)
            {
                bestRoute = currentRoute;
                bestNode = o;
            }
        }
        return bestNode;
    }

    public List<Action> getDijkstraPlan(
        Map<Map<Variable, Object>, Map<Variable, Object>> father,
        Map<Map<Variable, Object>, Action> plan,
        Map<Variable, Object> finalDest
    )
    {
        List<Action> res = new ArrayList<>();
        while (father.get(finalDest) != null)
        {
            res.add(plan.get(finalDest));
            finalDest = father.get(finalDest);
        }
        return res.reversed();
    }


}
