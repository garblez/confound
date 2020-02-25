package uk.ac.gla.confound.constraint;


import uk.ac.gla.confound.problem.Variable;

public abstract class Constraint {
    public Variable vi;
    public Variable vj;

    public Constraint() {}

    public Constraint(Variable vi, Variable vj) {
        this.vi = vi;
        this.vj = vj;
    }

    public boolean check() {
        return true;
    }


    public static AndConstraint and(Constraint a, Constraint b) {
        return new AndConstraint(a, b);
    }

    public static OrConstraint or(Constraint a, Constraint b) {
        return new OrConstraint(a, b);
    }

    public Constraint compose(Constraint a) {
        if (a == null) return this;
        return and(a, this);
    }

    public static Constraint compose(Constraint a, Constraint b) {
        if (a == null && b == null) return null;
        else if (a == null) return b;
        else if (b == null) return a;
        else return and(a, b);
    }
}
