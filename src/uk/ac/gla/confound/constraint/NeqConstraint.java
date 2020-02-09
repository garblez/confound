package uk.ac.gla.confound.constraint;

import uk.ac.gla.confound.problem.Variable;

public class NeqConstraint extends Constraint {

    public NeqConstraint(Variable a, Variable b) {
        super(a,b);
    }

    @Override
    public boolean check() {
        return this.vi.value != this.vj.value;
    }
}
