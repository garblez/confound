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


    public static void allDiff(ConstraintMatrix constraints, Variable... variables) {
        for (int i = 0; i < variables.length; i++) {
            for (int j = 0; j < variables.length; j++) {
                if (!variables[i].equals(variables[j]) && !variables[i].isNil() && !variables[j].isNil()) {
                    constraints.add(variables[i].index, variables[j].index, new NeqConstraint(variables[i], variables[j]));
                }
            }
        }
    }
}
