package uk.ac.gla.confound;


import uk.ac.gla.confound.examples.NQueens;
import uk.ac.gla.confound.examples.Sudoku;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.*;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws IOException
    {
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
        }
        Solver solver;
        Problem p;
        BufferedWriter w;

        for (int i = 4; i < 13; i++) {
            p = new NQueens(i);
            solver = new BacktrackSolver(p);
            System.out.println("Attempting to solve for "+i+"Queens");
            solver.solveAll();

            w = new BufferedWriter(
                    new FileWriter("/home/max/IndependentProject/src/uk/ac/gla/confound/solutions/nqueen/bt/sol"+i+".txt")
            );
            w.write(solver.stats.toString());
            w.close();
        }

        for (int i = 4; i < 13; i++) {
            p = new NQueens(i);
            solver = new ForwardCheckSolver(p);
            System.out.println("Attempting to solve for "+i+"Queens");
            solver.solveAll();

            w = new BufferedWriter(
                    new FileWriter("/home/max/IndependentProject/src/uk/ac/gla/confound/solutions/nqueen/fc/sol"+i+".txt")
            );
            w.write(solver.stats.toString());
            w.close();
        }

        for (int i = 4; i < 13; i++) {
            p = new NQueens(i);
            solver = new BackjumpSolver(p);
            System.out.println("Attempting to solve for "+i+"Queens");
            solver.solveAll();

            w = new BufferedWriter(
                    new FileWriter("/home/max/IndependentProject/src/uk/ac/gla/confound/solutions/nqueen/bj/sol"+i+".txt")
            );
            w.write(solver.stats.toString());
            w.close();
        }

        for (int i = 4; i < 13; i++) {
            p = new NQueens(i);
            solver = new ConflictBackjumpSolver(p);
            System.out.println("Attempting to solve for "+i+"Queens");
            solver.solveAll();

            w = new BufferedWriter(
                    new FileWriter("/home/max/IndependentProject/src/uk/ac/gla/confound/solutions/nqueen/cbj/sol"+i+".txt")
            );
            w.write(solver.stats.toString());
            w.close();
        }*/

        Problem p;
        try {
            //p = new Sudoku("/home/max/IndependentProject/src/uk/ac/gla/confound/examples/sudoku/key03.sudoku");
            p = new NQueens(8);
            Solver dbt = new DynamicBacktrackSolver(p);
            dbt.solveAll();
            dbt.report(p);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
