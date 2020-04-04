package uk.ac.gla.confound.examples.nqueens;

import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.BacktrackSolver;
import uk.ac.gla.confound.solver.DynamicBacktrackSolver;
import uk.ac.gla.confound.solver.Solver;

public class Solve {
    public static void main(String... args) {
        Problem p = new NQueens(8);
        Solver s = new BacktrackSolver(p);
        s.solveAll();
        s.report(p);
    }
}
