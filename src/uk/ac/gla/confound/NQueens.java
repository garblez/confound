package uk.ac.gla.confound;


import uk.ac.gla.confound.constraint.Constraint;
import uk.ac.gla.confound.constraint.IndexPair;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.*;


public class NQueens extends Problem {
    public NQueens(int numVars) {
        super(numVars);

        for (int i = 0; i < this.numVariables; i++) {
            for (int j = 0; j < this.numVariables; j++) {
                if (i != j)
                    constraints.putIfAbsent(new IndexPair(i, j), new QueenConstraint(this, i, j));
            }
        }
    }



    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("USAGE: NQueens [Solver] [Num Queens] ");
            System.out.println("BacktrackSolver, ForwardCheckSolver, BackjumpSolver, ConflictBackjumpSolver, DynamicBacktrackSolver");
        } else {
            Problem nQueens = new NQueens(Integer.parseInt(args[1]));
            Solver s;
            switch (args[0]) {
                case "ForwardCheckSolver":
                    s = new ForwardCheckSolver(nQueens);
                    break;
                case "BackjumpSolver":
                    s = new BackjumpSolver(nQueens);
                    break;
                case "ConflictBackjumpSolver":
                    s = new ConflictBackjumpSolver(nQueens);
                    break;
                /*case "DynamicBacktrackSolver":
                    s = new DynamicBacktrackSolver();
                    break;

                 */
                case "BacktrackSolver":
                    // FALL-THROUGH
                default:
                    s = new BacktrackSolver(nQueens);

            }
            s.solve();
            s.report(nQueens);
        }
    }

    public static class QueenConstraint extends Constraint {

        public QueenConstraint(Problem p, int i, int j) {
            super(p, i, j);
        }

        @Override
        public boolean check() {
            return !p.variables[i].equals(p.variables[j])
                    && Math.abs(p.variables[i].value - p.variables[j].value) != Math.abs(i - j);
        }
    }
}
