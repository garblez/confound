package uk.ac.gla.confound.marktwo;

import uk.ac.gla.confound.marktwo.trailing.BacktrackSolver;

public class Main {
    public static void main(String... args) {
        Problem fourQueens = new NQueens(2);
        Solver btSolver = new BacktrackSolver(fourQueens);
        btSolver.solve();
        btSolver.report(fourQueens);
    }
}
