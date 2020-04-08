package uk.ac.gla.confound.examples.nqueens;

import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.*;

public class Solve {
    public static void main(String... args) {
        Problem p = new NQueens(8);
        Solver s = new DynamicBacktrackSolver(p);
        s.solve();
        s.report(p);
        System.out.println(p.solutions.size());
    }
}
