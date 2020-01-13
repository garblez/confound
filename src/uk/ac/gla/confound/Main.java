package uk.ac.gla.confound;


public class Main {
    public static void main(String[] args)
    {


        NQueens eightQueens = new NQueens(4);
        Solver btSolver = new BacktrackSolver(eightQueens);
        btSolver.solve();
        btSolver.report(eightQueens);

        NQueens eightQueensFC = new NQueens(4);
        Solver fcSolver = new ForwardChecker(eightQueensFC);
        fcSolver.solve();
        fcSolver.report(eightQueensFC);


    }
}
