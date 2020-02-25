package uk.ac.gla.confound.solver;

import org.junit.internal.Classes;
import uk.ac.gla.confound.examples.CrystalMaze;
import uk.ac.gla.confound.examples.NQueens;
import uk.ac.gla.confound.examples.Sudoku;
import uk.ac.gla.confound.problem.Problem;

import java.util.*;

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

    int numIterations;
    int numSolutions;
    int backtracks;
    double duration;

    public Solver(Problem p) {
        numIterations = 0;
        numSolutions = 0;
        backtracks = 0;
        this.p = p;
    }

    public void solveFor(int n)
    {
        duration = System.currentTimeMillis();
        status = Status.UNKNOWN;
        p.consistent = true;
        int i = 1;

        while (status == Status.UNKNOWN) {
            if (p.consistent) {
                i = label(i);
            } else {
                i = unlabel(i);
                ++backtracks;
            }

            if (i > p.numVariables) {
                ++numSolutions;    // Now we've found one iteration, we try to find another until there is none

                int[] solution = new int[p.variables.length-1];
                for (int j = 1; j < p.variables.length; j++) {
                    solution[j-1] = p.variables[j].value;
                }
                p.solutions.add(solution);

                i -= 1;
                p.consistent = false;

                if (p.solutions.size() == n)
                    break;

            } else if (i == 0)
                status = Status.IMPOSSIBLE;

            ++numIterations;
        }
        duration = System.currentTimeMillis() - duration;
    }

    public void solveAll()
    {
        duration = System.currentTimeMillis();
        status = Status.UNKNOWN;
        p.consistent = true;

        int i = 1;

        while (status == Status.UNKNOWN) {
            if (p.consistent) {
                i = label(i);
            } else {
                i = unlabel(i);
                ++backtracks;
            }

            if (i > p.numVariables) {
                ++numSolutions;    // Now we've found one iteration, we try to find another until there is none

                int[] solution = new int[p.variables.length-1];
                for (int j = 1; j < p.variables.length; j++) {
                    solution[j-1] = p.variables[j].value;
                }
                p.solutions.add(solution);

                i -= 1;
                p.consistent = false;
            } else if (i == 0)
                status = Status.IMPOSSIBLE;

            ++numIterations;
        }
        duration = System.currentTimeMillis() - duration;
    }

    public void report(Problem p)
    {

        System.out.println("Status report for "+NAME);
        System.out.println("#Iterations: "+numIterations);
        System.out.println("Duration: " + 0.001*duration + "(s)");
        System.out.println("Backtracks: " + backtracks);
        System.out.println("#Solutions: "+numSolutions);
        System.out.println("Solutions are as follows\n=========================");
        for (int[] arr: p.solutions) {
            System.out.print("[");
            for (int x : arr) {
                System.out.print(x + ", ");
            }
            System.out.println("\b\b]");
        }
        System.out.println("=========================");

    }


    // Usage: [Solver] [Problem] {-n [num vars]}
    public static void main(String... args) {
        int n = 0;


        if (args.length < 2) {
            System.out.println("Insufficient arguments supplied:\nUsage: [Solver] [Problem] {-n [num vars]}");

        } else {
            Problem p;
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
                    if (args.length != 2) System.exit(-1);
                    p = new Sudoku();
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

            s.solveFor(3);
            s.report(p);

        }



    }

}
