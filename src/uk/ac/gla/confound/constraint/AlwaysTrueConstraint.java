package uk.ac.gla.confound.constraint;

public class AlwaysTrueConstraint extends Constraint {

    // This constraint does not record any variable indices or problem, it just exists to return true on check()
    public AlwaysTrueConstraint() {
        super();
    }
}
