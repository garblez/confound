package uk.ac.gla.confound;

import uk.ac.gla.confound.Variable;

public abstract class BaseConstraint {
    Variable[] variables;

    public BaseConstraint(Variable[] vars) {
        variables = vars;
    }

    public boolean check(int i, int j) {
        return true;
    }
}
