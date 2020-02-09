package uk.ac.gla.confound.constraint;

public class OrConstraint extends CompositeConstraint {

    public OrConstraint(Constraint a, Constraint b) {
        super(a, b);
    }

    @Override
    public boolean check() {
        return a.check() || b.check();
    }
}
