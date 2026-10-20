package planning;

import java.util.*;
import modelling.Variable;

// Exercice 12 (suite) : comparaison sur une grille 15x15.
// Le robot va de la case (0,0) a la case (14,14) en se deplacant dans 4 directions.
// Un mur en diagonale laisse un seul passage. Avec plusieurs directions possibles,
// on voit mieux l'interet de l'heuristique de A*.
public class ExperienceGrille {

    public static void main(String[] args) {

        System.out.println("### Grille 15x15 avec mur en diagonale ###\n");

        int size = 15;

        Variable row = new Variable("row", domain(size));
        Variable column = new Variable("column", domain(size));

        // le mur est la diagonale, sauf la case du milieu qui sert de passage
        Set<String> walls = new HashSet<>();
        for (int i = 1; i < size - 1; i++) {
            if (i != size / 2) {
                walls.add(i + "," + i);
            }
        }

        Set<Action> actions = new LinkedHashSet<>();

        // pour chaque case libre, une action vers chaque voisine libre (cout 1)
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (walls.contains(r + "," + c)) {
                    continue;
                }
                addMove(actions, walls, row, column, size, r, c, r, c + 1); // droite
                addMove(actions, walls, row, column, size, r, c, r + 1, c); // bas
                addMove(actions, walls, row, column, size, r, c, r, c - 1); // gauche
                addMove(actions, walls, row, column, size, r, c, r - 1, c); // haut
            }
        }

        Map<Variable, Object> initialState = Map.of(row, 0, column, 0);
        Goal goal = new BasicGoal(Map.of(row, size - 1, column, size - 1));

        // distance de Manhattan jusqu'au but
        int target = size - 1;
        Heuristic heuristic = state -> {
            int r = (Integer) state.get(row);
            int c = (Integer) state.get(column);
            return Math.abs(r - target) + Math.abs(c - target);
        };

        System.out.printf("%-12s %-16s %-12s %-12s%n", "Planner", "Noeuds explores", "Cout plan", "Nb actions");

        printRow("DFS", new DFSPlanner(initialState, actions, goal));
        printRow("BFS", new BFSPlanner(initialState, actions, goal));
        printRow("Dijkstra", new DijkstraPlanner(initialState, actions, goal));
        printRow("A*", new AStarPlanner(initialState, actions, goal, heuristic));
    }

    // ajoute l'action (fromRow, fromColumn) -> (toRow, toColumn) si la case d'arrivee existe et n'est pas un mur
    private static void addMove(Set<Action> actions, Set<String> walls, Variable row, Variable column,
                                int size, int fromRow, int fromColumn, int toRow, int toColumn) {

        boolean inGrid = toRow >= 0 && toRow < size && toColumn >= 0 && toColumn < size;

        if (inGrid && !walls.contains(toRow + "," + toColumn)) {
            actions.add(new BasicAction(
                    Map.of(row, fromRow, column, fromColumn),
                    Map.of(row, toRow, column, toColumn),
                    1));
        }
    }

    // le domaine {0, ..., size - 1}
    private static Set<Object> domain(int size) {
        Set<Object> domain = new HashSet<>();
        for (int i = 0; i < size; i++) {
            domain.add(i);
        }
        return domain;
    }

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
