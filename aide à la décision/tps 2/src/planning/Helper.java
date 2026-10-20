package planning;

import modelling.Variable;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

public class Helper
{
    public static List<Action> backtrack(
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

    public static Map<Variable, Object> argmin(Set<Map<Variable, Object>> open, Map<Map<Variable, Object>, Float> distances)
    {
        Map<Variable, Object> bestNode = new HashMap<>();
        Float bestRoute = Float.MAX_VALUE;

        for(Map<Variable, Object> o : open)
        {
            Float currentRoute = distances.get(o);
            if (currentRoute < bestRoute)
            {
                bestRoute = currentRoute;
                bestNode = o;
            }
        }
        return bestNode;
    }
}