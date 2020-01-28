package uk.ac.gla.confound.solver;

import uk.ac.gla.confound.Variable;

public class NQueenConstraint extends BaseConstraint {

    public NQueenConstraint(Variable[] vars) {
        super(vars);
    }

    @Override
    public boolean check(int i, int j) {
        return !variables[i].equals(variables[j]) && Math.abs(variables[i].value - variables[j].value) != Math.abs(i - j);
    }
}
