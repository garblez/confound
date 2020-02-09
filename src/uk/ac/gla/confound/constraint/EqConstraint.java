package uk.ac.gla.confound.constraint;

import uk.ac.gla.confound.problem.Variable;

public class EqConstraint extends Constraint {

    public EqConstraint(Variable a, Variable b) {
        super(a,b);
    }

    @Override
    public boolean check() {
        return vi.value == vj.value;
    }
}
