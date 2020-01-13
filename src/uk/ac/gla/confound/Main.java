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

        Domain d = new Domain(4);
        Variable v = new Variable(d);
        System.out.println(v.currentDomain);
        v.currentDomain.remove(new Integer(2));
        System.out.println(v.currentDomain);

    }
}
