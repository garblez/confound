package uk.ac.gla.confound;


import uk.ac.gla.confound.solver.*;

import java.util.Map;

public class Main {
    public static void main(String[] args)
    {
        int N = 4;



        NQueens eightQueens = new NQueens(N);
        Solver btSolver = new BacktrackSolver(eightQueens);
        btSolver.solve();
        btSolver.report(eightQueens);


        NQueens eightQueensFC = new NQueens(N);
        Solver fcSolver = new ForwardCheckSolver(eightQueensFC);
        fcSolver.solve();
        fcSolver.report(eightQueensFC);

        NQueens bjQueens = new NQueens(N);
        Solver bjSolver = new BackjumpSolver(bjQueens);
        bjSolver.solve();
        bjSolver.report(bjQueens);



        NQueens cbjQueens = new NQueens(N);
        Solver cbjSolver = new ConflictBackjumpSolver(cbjQueens);
        cbjSolver.solve();
        cbjSolver.report(cbjQueens);

    }
}
