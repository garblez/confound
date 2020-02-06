package uk.ac.gla.confound;


import uk.ac.gla.confound.constraint.Constraint;
import uk.ac.gla.confound.problem.Problem;



public class NQueens extends Problem {
    public NQueens(int numVars) {
        super(numVars);

        Constraint queenConstraint = new QueenConstraint();

        for (int i = 1; i <= this.numVariables; i++) {
            for (int j = 1; j <= this.numVariables; j++) {
                if (i != j) {
                    constraints[i][j] = queenConstraint;
                }
            }
        }
    }



    public class QueenConstraint implements Constraint {
        public boolean check(int i, int j, int vali, int valj) {
            return vali != valj
                    && Math.abs(vali - valj) != Math.abs(i - j);
        }
    }
}
