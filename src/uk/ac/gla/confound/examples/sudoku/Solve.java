package uk.ac.gla.confound.examples.sudoku;

import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.*;

import java.io.IOException;

public class Solve {
    public static void main(String... args) throws IOException {
        Problem p = new Sudoku("/home/max/IndependentProject/confound/src/uk/ac/gla/confound/examples/sudoku/prob0.sudoku");
        Solver bt = new BacktrackSolver(p);
        bt.solveFor(1);
        bt.report(p);
    }
}
