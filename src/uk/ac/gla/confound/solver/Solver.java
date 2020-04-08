package uk.ac.gla.confound.solver;

import uk.ac.gla.confound.examples.crystalMaze.CrystalMaze;
import uk.ac.gla.confound.examples.nqueens.NQueens;
import uk.ac.gla.confound.examples.sudoku.Sudoku;
import uk.ac.gla.confound.problem.Problem;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Abstract base class Solver defines common variables and methods between all extending solvers such as
 * Problem p, the problem class being solved
 * Status status, the current status of the search undertaken by the solver
 * int numIterations, the number of search iterations undertaken
 * int numSolutions, the number of consistent solutions found
 * ArrayList previousValues, a list of the previous values found
 */
public abstract class Solver implements SolverMethods {
    public static String NAME = "Base Solver";
    public Problem p;
    public Status status;
    public Statistics stats;
    public ArrayList<Integer> path = new ArrayList<>();

    public Solver(Problem p) {
        stats = new Statistics(p.numVariables);
        this.p = p;
    }

    public void solve(int n)
    {
        stats.setCurrentTime(System.currentTimeMillis());
        status = Status.UNKNOWN;
        p.consistent = true;
        int i = 1;

        while (status == Status.UNKNOWN) {
            if (p.consistent) {
                i = label(i);
            } else {
                i = unlabel(i);
                stats.incBacktracks();
            }

            if (i > p.numVariables) {
                stats.incSolutions(); // Now we've found one iteration, we try to find another until there is none

                int[] solution = new int[p.variables.length-1];
                for (int j = 1; j < p.variables.length; j++) {
                    solution[j-1] = p.variables[j].value;
                }
                p.solutions.add(solution);

                i -= 1;
                p.consistent = false;

                if (p.solutions.size() == n) {
                    status = Status.SOLUTION;  // Causes the solver to break from searching
                }

            } else if (i == 0)
                status = Status.IMPOSSIBLE;

            stats.incNodesVisited();
            stats.markVisited(i);
        }
        stats.findDuration();
    }

    public void solve()
    {
        stats.setCurrentTime(System.currentTimeMillis());
        status = Status.UNKNOWN;
        p.consistent = true;

        int i = 1;

        // Keep searching for the first/next solution
        while (status == Status.UNKNOWN || status == Status.SOLUTION) {
            if (p.consistent) {
                i = label(i);
            } else {
                i = unlabel(i);
                stats.incBacktracks();
            }

            if (i > p.numVariables) {
                stats.incSolutions(); // Now we've found one iteration, we try to find another until there is none

                int[] solution = new int[p.variables.length-1];
                for (int j = 1; j < p.variables.length; j++) {
                    solution[j-1] = p.variables[j].value;
                }
                p.solutions.add(solution);
                i -= 1;
                p.consistent = false;
                status = Status.SOLUTION;
            } else if (i == 0)
                status = Status.IMPOSSIBLE;

            stats.incNodesVisited();
            stats.markVisited(i);
            //addToPath(i);
        }
        stats.findDuration();
    }

    public void printStats() {
        System.out.println(stats+"\n");
    }

    public void report(Problem p)
    {
        System.out.println(p.solutions.size() > 0 ? Status.SOLUTION : status);

            System.out.println(p.solutions.size() + " solutions as follows");
            for (int i = 0; i < p.solutions.size(); i++)
                p.print(i);
            System.out.println();
            System.out.println("=--------------------------=");

    }

    public void addToPath(int index) {
        path.add(index);
    }

    // Usage: [Solver] [Problem] {-n [num vars]}
    public static void main(String... args) {
        int n = 0;


        if (args.length < 2) {
            System.out.println("Insufficient arguments supplied:\nUsage: [Solver] [Problem] {-n [num vars]}");

        } else {
            Problem p = null;
            Solver s;

            switch (args[1]) {
                case "NQueens":
                    if (args.length != 4) {
                        System.out.println("Could not infer number of queens");
                        System.exit(-1);
                    }
                    n = Integer.parseInt(args[3]);
                    p = new NQueens(n);
                    break;
                case "CrystalMaze":
                    if (args.length != 4) System.exit(-1);
                    p  = new CrystalMaze(Integer.parseInt(args[3]));
                    break;
                case "Sudoku":
                    if (args.length != 3) System.exit(-1);
                    try {
                        p = new Sudoku(args[2]);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                    break;
                default:
                    System.out.println("Invalid problem argument");
                    p = null;
            }



            switch (args[0]) {
                case "ForwardCheckSolver":
                    s = new ForwardCheckSolver(p);
                    break;
                case "BackjumpSolver":
                    s = new BackjumpSolver(p);
                    break;
                case "ConflictBackjumpSolver":
                    s = new ConflictBackjumpSolver(p);
                    break;
                case "BacktrackSolver":
                    s = new BacktrackSolver(p);
                    break;
                default:
                    s = null;
            }
            if (s == null || p == null) System.exit(-1);

            s.solve();
            s.report(p);
            s.printStats();
        }



    }

}
