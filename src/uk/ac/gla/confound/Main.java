package uk.ac.gla.confound;


import uk.ac.gla.confound.examples.NQueens;
import uk.ac.gla.confound.examples.Sudoku;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;


public class Main {
    public static void main(String[] args)
    {
        Problem p = null;
        /*
        try {
            //p = new Sudoku("/home/max/IndependentProject/src/uk/ac/gla/confound/examples/sudoku/key03.sudoku");
            p = new NQueens(8);
            Solver btS = new ConflictBackjumpSolver(p);
            btS.solveAll();
            btS.report(p);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }*/

        p = new NQueens(4);
        Solver btS = new BacktrackSolver(p);
        btS.solveAll();
        System.out.println(btS.p.solutions.size());
        btS.report(p);

    }
}
