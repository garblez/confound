package uk.ac.gla.confound.examples.sudoku;

import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.*;

import java.io.IOException;
import java.nio.file.Paths;

public class Solve {
    public static void main(String... args) throws IOException {
        Problem p = new Sudoku(Paths.get(".").toAbsolutePath().normalize().toString() +
                "/"+"src/uk/ac/gla/confound/examples/sudoku/prob0.sudoku");
        Solver bt = new BacktrackSolver(p);
        bt.solve(1);
        bt.report(p);
    }
}
