package uk.ac.gla.confound;


import uk.ac.gla.confound.examples.CrystalMaze;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.*;


public class Main {
    public static void main(String[] args)
    {
        Problem p = new CrystalMaze(8);
        Solver btS = new BackjumpSolver(p);
        btS.solveAll();
        btS.report(p);


    }
}
