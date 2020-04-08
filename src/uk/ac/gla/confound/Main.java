package uk.ac.gla.confound;


import uk.ac.gla.confound.examples.sudoku.Sudoku;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.*;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException
    {

        Problem p;
        try {
            p = new Sudoku("../examples/sudoku/almost.sudoku");
            //p = new NQueens(8);
            Solver dbt = new DynamicBacktrackSolver(p);
            dbt.solve();
            dbt.report(p);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
