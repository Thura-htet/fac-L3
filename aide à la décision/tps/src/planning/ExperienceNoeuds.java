package planning;

import java.util.*;
import modelling.Variable;

// Exercice 12 : on compare le nombre de noeuds explores par les 4 planificateurs
// sur un couloir de n cases (le robot va de la case 1 a la case n).
// Dans les experiences 2 et 3 il y a un raccourci tres couteux de 1 vers n.
public class ExperienceNoeuds {

    public static void main(String[] args) {

        System.out.println("### Experience 1 : couloir de 10 cases, sans raccourci ###\n");
        experience(10, 0);

        System.out.println("\n### Experience 2 : couloir de 10 cases, raccourci de cout 50 ###\n");
        experience(10, 50);

        System.out.println("\n### Experience 3 : couloir de 30 cases, raccourci de cout 100 ###\n");
        experience(30, 100);
    }

    // shortcutCost = 0 veut dire qu'il n'y a pas de raccourci
    private static void experience(int size, int shortcutCost) {

        Set<Object> domain = new HashSet<>();
        for (int i = 1; i <= size; i++) {
            domain.add(i);
        }
        Variable position = new Variable("position", domain);

        // LinkedHashSet pour garder l'ordre des actions (resultats reproductibles)
        Set<Action> actions = new LinkedHashSet<>();

        // avancer et reculer d'une case, cout 1
        for (int i = 1; i < size; i++) {
            actions.add(new BasicAction(Map.of(position, i), Map.of(position, i + 1), 1));
            actions.add(new BasicAction(Map.of(position, i + 1), Map.of(position, i), 1));
        }

        if (shortcutCost > 0) {
            actions.add(new BasicAction(Map.of(position, 1), Map.of(position, size), shortcutCost));
        }

        Map<Variable, Object> initialState = Map.of(position, 1);
        Goal goal = new BasicGoal(Map.of(position, size));

        // distance restante jusqu'a la case size (ne depasse jamais le vrai cout)
        Heuristic heuristic = state -> Math.abs((Integer) state.get(position) - size);

        System.out.printf("%-12s %-16s %-12s %-12s%n", "Planner", "Noeuds explores", "Cout plan", "Nb actions");

        printRow("DFS", new DFSPlanner(initialState, actions, goal));
        printRow("BFS", new BFSPlanner(initialState, actions, goal));
        printRow("Dijkstra", new DijkstraPlanner(initialState, actions, goal));
        printRow("A*", new AStarPlanner(initialState, actions, goal, heuristic));
    }

    // une methode par planificateur car activateNodeCount et getNodeCount
    // ne sont pas dans l'interface Planner

    private static void printRow(String name, DFSPlanner planner) {
        planner.startClock();
        List<Action> plan = planner.plan();
        printResult(name, planner.getClock(), plan);
    }

    private static void printRow(String name, BFSPlanner planner) {
        planner.startClock();
        List<Action> plan = planner.plan();
        printResult(name, planner.getClock(), plan);
    }

    private static void printRow(String name, DijkstraPlanner planner) {
        planner.startClock();
        List<Action> plan = planner.plan();
        printResult(name, planner.getClock(), plan);
    }

    private static void printRow(String name, AStarPlanner planner) {
        planner.startClock();
        List<Action> plan = planner.plan();
        printResult(name, planner.getClock(), plan);
    }

    private static void printResult(String name, int nodeCount, List<Action> plan) {

        int cost = 0;
        int nbActions = 0;

        if (plan != null) {
            nbActions = plan.size();
            for (Action action : plan) {
                cost += action.getCost();
            }
        }

        System.out.printf("%-12s %-16d %-12d %-12d%n", name, nodeCount, cost, nbActions);
    }
}
