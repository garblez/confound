package uk.ac.gla.confound;


public class Main {
    public static void main(String[] args)
    {
        /*
        double time;
        NQueens nQueens = new NQueens(4);

        Solver btSolver = new BacktrackSolver(nQueens);

        time = System.currentTimeMillis();
        btSolver.solve();
        time = System.currentTimeMillis() - time;

        // Solutions from solvers are actually views into the variables of the problem from index 1 onwards.
        // If called before solve() then the previous values for the problem (potentially incorrect values) are shown.
        System.out.println(time);
        btSolver.report(nQueens);
        */

        NQueens eightQueens = new NQueens(4);
        Solver fcSolver = new ForwardChecker(eightQueens);
        fcSolver.solve();
        fcSolver.report(eightQueens);
    }
}
