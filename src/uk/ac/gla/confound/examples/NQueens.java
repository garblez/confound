package uk.ac.gla.confound.examples;


import uk.ac.gla.confound.constraint.Constraint;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.problem.Variable;


public class NQueens extends Problem {
    public NQueens(int numVars) {
        super(numVars);


        for (int i = 1; i <= this.numVariables; i++) {
            for (int j = 1; j <= this.numVariables; j++) {
                if (i != j) {
                    constraints[i][j] = new QueenConstraint(variables[i], variables[j]);
                }
            }
        }
    }



    public class QueenConstraint extends Constraint {

        public QueenConstraint(Variable a, Variable b) {
            super(a,b);
        }

        public boolean check() {
            return vi.value != vj.value
                    && Math.abs(vi.value - vj.value) != Math.abs(vi.index - vj.index);
        }
    }
}