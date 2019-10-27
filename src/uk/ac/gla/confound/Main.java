package uk.ac.gla.confound;


public class Main {
    public static void main(String[] args)
    {
        NQueens nQueens = new NQueens(4);

        BacktrackSolver btSolver = new BacktrackSolver();
        btSolver.solve(nQueens);
        // Solutions from solvers are actually views into the variables of the problem from index 1 onwards.
        // If called before solve() then the previous values for the problem (potentially incorrect values) are shown.
        System.out.println(btSolver.solution());
    }
}
