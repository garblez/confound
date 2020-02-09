package uk.ac.gla.confound.constraint;

import uk.ac.gla.confound.problem.Variable;

public class AndConstraint extends CompositeConstraint {

    public AndConstraint(Constraint a, Constraint b) {
        super(a, b);
    }

    @Override
    public boolean check() {
        return a.check() && b.check();
    }
}
