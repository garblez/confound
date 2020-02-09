package uk.ac.gla.confound.examples;

import uk.ac.gla.confound.constraint.Constraint;
import uk.ac.gla.confound.problem.Domain;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.problem.Variable;

public class Sudoku extends Problem {
    public Sudoku(int numVariables) {
        super(numVariables*numVariables);
        Domain dom = new Domain(1,9);
        for (int i = 0; i < numVariables*numVariables; i++)
            variables[i] = new Variable(dom,i);


    }

    class NeqConstraint extends Constraint {

        public NeqConstraint(Variable vi, Variable vj) {
            super(vi, vj);
        }

        @Override
        public boolean check() {
            return vi.value != vj.value;
        }
    }
}
