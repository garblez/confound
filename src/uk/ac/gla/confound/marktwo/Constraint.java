package uk.ac.gla.confound;


import java.util.function.Function;

// We map variables involved in the constraint to the constraint requirements:
// we can therefore check if the group of variables satisfy the constraint.

public class Constraint {
    public Function<Variable[], Boolean> condition;
    public Variable[] variable;

    public boolean check() {
        return condition.apply(variable);
    }

    public Constraint(Variable... variable) {
        this.variable = variable;
    }

    public Constraint where(Function<Variable[], Boolean> condition) {
        this.condition = condition;
        return this;
    }

}
