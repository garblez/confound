package uk.ac.gla.confound;


import uk.ac.gla.confound.constraint.AlwaysTrueConstraint;
import uk.ac.gla.confound.constraint.Constraint;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.*;


public class NQueens extends Problem {
    public NQueens(int numVars) {
        super(numVars);

        for (int i = 0; i < this.numVariables; i++) {
            for (int j = 0; j < this.numVariables; j++) {
                if (i != j) {
                    constraints[i][j] = new QueenConstraint(this, i, j);
                } else {
                    constraints[i][j] = new AlwaysTrueConstraint();
                }
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
        int ix0;
        int ix1;

        public QueenConstraint(Problem p, int i, int j) {
            super(p, i, j);
            ix0 = i;
            ix1 = j;
        }

        @Override
        public boolean check() {
            return !var0.equals(var1)
                    && Math.abs(var0.value - var1.value) != Math.abs(ix0 - ix1);
        }
    }
}
