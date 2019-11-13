package uk.ac.gla.confound;


public class Main {
    public static void main(String[] args)
    {
        //NQueens nQueens = new NQueens(8);

        // btSolver = new BacktrackSolver(nQueens);
        //btSolver.solve();
        // Solutions from solvers are actually views into the variables of the problem from index 1 onwards.
        // If called before solve() then the previous values for the problem (potentially incorrect values) are shown.

        //btSolver.report(nQueens);

        NQueens eightQueens = new NQueens(8);
        ForwardcheckSolver fcSolver = new ForwardcheckSolver(eightQueens);
        fcSolver.solve();
        fcSolver.report(eightQueens);
    }
}
