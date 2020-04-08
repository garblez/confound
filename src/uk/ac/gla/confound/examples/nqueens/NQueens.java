package uk.ac.gla.confound.examples.nqueens;


import uk.ac.gla.confound.constraint.Constraint;
import uk.ac.gla.confound.constraint.ConstraintList;
import uk.ac.gla.confound.problem.Domain;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.problem.Variable;


public class NQueens extends Problem {
    public NQueens(int numVars) {
        super(new Domain(0, numVars-1), numVars);

        Constraint.allDiff(constraints, variables);

        for (int i = 1; i <= this.numVariables; i++) {
            for (int j = 1; j <= this.numVariables; j++) {
                if (i != j) {
                    constraints.add(i, j, new QueenConstraint(variables[i], variables[j]));
                }
            }
        }
    }

    @Override
    public void print(int x)
    {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < numVariables; i++) {
            for (int j = 0; j < numVariables; j++) {
                if (j==solutions.get(x)[i])
                    s.append("*");
                else
                    s.append("  ");
            }
            s.append("\n");
        }
        for (int i = 0; i < numVariables; i++)
            s.append("==");
        s.append("\n");
        System.out.print(s.toString());
    }


    public class QueenConstraint extends Constraint {

        public QueenConstraint(Variable a, Variable b) {
            super(a,b);
        }

        public boolean check() {
            return Math.abs(vi.value - vj.value) != Math.abs(vi.index - vj.index);
        }
    }
}
