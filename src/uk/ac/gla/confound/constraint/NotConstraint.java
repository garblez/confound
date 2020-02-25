package uk.ac.gla.confound.constraint;

import uk.ac.gla.confound.problem.Variable;

public class NotConstraint extends Constraint {
    int constant;
    public NotConstraint(Variable v, int constant) {
        vi = v;
        this.constant = constant;
    }

    @Override
    public boolean check() {
        return vi.value != constant;
    }
}
