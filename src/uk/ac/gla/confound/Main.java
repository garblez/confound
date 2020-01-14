package uk.ac.gla.confound;


public class Main {
    public static void main(String[] args)
    {

/*
        NQueens eightQueens = new NQueens(8);
        Solver btSolver = new BacktrackSolver(eightQueens);
        btSolver.solve();
        btSolver.report(eightQueens);

        NQueens eightQueensFC = new NQueens(8);
        Solver fcSolver = new ForwardChecker(eightQueensFC);
        fcSolver.solve();
        fcSolver.report(eightQueensFC);

        NQueens bjQueens = new NQueens(8);
        Solver bjSolver = new BackjumpSolver(bjQueens);
        bjSolver.solve();
        bjSolver.report(bjQueens);

 */

        NQueens cbjQueens = new NQueens(4);
        Solver cbjSolver = new ConflictBackjumpSolver(cbjQueens);
        cbjSolver.solve();
        cbjSolver.report(cbjQueens);
    }
}
