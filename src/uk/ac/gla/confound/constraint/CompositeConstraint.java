package uk.ac.gla.confound.constraint;

public abstract class CompositeConstraint extends Constraint {
    Constraint a;
    Constraint b;

    public CompositeConstraint(Constraint a, Constraint b) {
        this.a = a;
        this.b = b;
    }

}
