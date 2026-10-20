package planning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import modelling.Variable;

public class SixDegrees
{
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        if (args.length < 0)
            return;

        // load csv file into memory
        Map<String, Set<String>> actorRecord = new HashMap<>();
        Map<String, List<String>> filmRecord = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/films.csv")))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] values = line.split(",");
                actorRecord.put(values[0], new HashSet<>());
                for (int i=1; i<values.length; i++)
                {
                    actorRecord.get(values[0]).add(values[i]);
                    if (!filmRecord.containsKey(values[i]))
                        filmRecord.put(values[i], new ArrayList<>());
                    filmRecord.get(values[i]).add(values[0]);
                }
            }
        }

        /*
        for (Map.Entry<String, List<String>> film : filmRecord.entrySet())
        {
            System.out.println(film.getKey() + " => " + film.getValue());
        }
        */


        final Variable varObj = new Variable("actor", new HashSet<>(actorRecord.keySet()));
        Set<Action> actions = new HashSet<>();
        Map<Map<String, String>, String> filmMap = new HashMap<>();

        for (Map.Entry<String, List<String>> film : filmRecord.entrySet())
        {
            int n = film.getValue().size();
            // if the film only feature one actor from sample data, skip the film
            if (n <= 1)
                continue;
            for (int i=0; i<n; i++)
            {
                for (int j=0; j<n; j++)
                {
                    if (i != j)
                    {
                        actions.add(
                            new BasicAction(
                                Map.of(varObj, film.getValue().get(i)), 
                                Map.of(varObj, film.getValue().get(j)),
                                1)
                        );
                        Map<String, String> actorPair = new HashMap<>();
                        actorPair.put(film.getValue().get(i), film.getValue().get(j));
                        filmMap.put(actorPair, film.getKey());
                    }
                        
                }
            }
        }

        String command = args[0].toLowerCase();
        Map<Variable, Object> start;
        Goal goal;
        System.out.println();
        switch (command)    
        {
            case "list":
                System.out.println("Here is the list of actors in the sample:");
                for (String actor : actorRecord.keySet())
                    System.out.println(actor);
                return;

            case "find":
                System.out.println("Finding the actor from Kevin Bacon...");
                start = Map.of(varObj, args[2]);
                goal = new BasicGoal(Map.of(varObj, "Kevin Bacon"));
                break;

            case "between":
               System.out.println("Finding the degree of separation between the actors..."); 
               start = Map.of(varObj, args[2]);
               goal = new BasicGoal(Map.of(varObj, args[3]));
               break;

            default:
                start = Map.of(varObj, "Bill Paxton");
                goal = new BasicGoal(Map.of(varObj, "Kevin Bacon"));
        }

        Heuristic heuristic = state -> {
            if (goal.isSatisfiedBy(state))
                return 0;
            return 1;
        };
        String algo = args[1].toUpperCase();
        System.out.println(algo);

        Map<String, Planner> planners = new HashMap<>();
        switch (algo) {
            case "DFS":
                planners.put("DFS", new DFSPlanner(start, actions, goal));
                break;

            case "BFS":
                planners.put("BFS", new BFSPlanner(start, actions, goal));
                break;

            case "DIJKSTRA":
                planners.put("DIJKSTRA", new DijkstraPlanner(start, actions, goal));
                break;

            case "ASTAR":
                planners.put("ASTAR", new AStarPlanner(start, actions, goal, heuristic));
                break;

            case "ALL":
                planners.put("DFS", new DFSPlanner(start, actions, goal));
                planners.put("BFS", new BFSPlanner(start, actions, goal));
                planners.put("DIJKSTRA", new DijkstraPlanner(start, actions, goal));
                planners.put("ASTAR", new AStarPlanner(start, actions, goal, heuristic));
                break;
        }

        printHeader();
        for (Map.Entry<String, Planner> e : planners.entrySet())
        {
            System.out.println("-".repeat(122));
            Planner planner = e.getValue();
            long startTime = System.nanoTime();

            planner.startClock();
            List<Action> res = planner.plan();

            long endTime = System.nanoTime();

            double elapsedMs = (endTime - startTime) / 1_000_000.0;
            printResult(e.getKey(), planner.getClock(), res.size(), elapsedMs, res, varObj, filmMap);
            System.out.println("-".repeat(122));
        }
        System.out.println();
        System.out.println();
    }

    public static void printHeader()
    {
        System.out.println();
        System.out.println();
        System.out.printf(
        "%-12s | %18s | %15s | %15s | %50s%n",
        "Algorithm", "Explored nodes", "Num steps", "Time (ms)", "Steps"
        );
        System.out.println("-".repeat(122));
    }

    public static void printResult(String algo, int nodes, int steps, double time, List<Action> plan, Variable varObj, Map<Map<String, String>, String> filmMap)
    {
        if (plan.isEmpty())
            return;

        Map<String, String> actorPair = new HashMap<>();
        actorPair.put((String) plan.get(0).getPrecondition().get(varObj), ((String) plan.get(0).getEffect().get(varObj)));
        System.out.printf(
            "%-12s | %18d | %15d | %15.2f | %50s%n",
            algo, nodes, steps, time, 
            ((String) plan.get(0).getPrecondition().get(varObj)) + " => " + ((String) plan.get(0).getEffect().get(varObj)) + "[" + filmMap.get(actorPair) + "]"
        );

        for (int i=1; i<steps; i++)
        {
            actorPair.put((String) plan.get(i).getPrecondition().get(varObj), ((String) plan.get(i).getEffect().get(varObj)));
            String step = ((String) plan.get(i).getPrecondition().get(varObj)) + " => " + ((String) plan.get(i).getEffect().get(varObj)) + "[" + filmMap.get(actorPair) + "]";
            System.out.printf(
                "%-12s | %18s | %15s | %15s | %50s%n",
                "", "", "", "", step
            );
        }
            
    }
}