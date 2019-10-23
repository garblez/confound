package uk.ac.gla.confound;


import java.util.function.Function;

// We map variables involved in the constraint to the constraint requirements:
// we can therefore check if the group of variables satisfy the constraint.

public class Constraint<T extends Variable> {
    public Function<T[], Boolean> condition;
    public T[] variables;

    public boolean check() {
        return condition.apply(variables);
    }

    public Constraint(T... variables) {
        this.variables = variables;
    }

    public Constraint where(Function<T[], Boolean> condition) {
        this.condition = condition;
        return this;
    }

    public static void main(String[] args){
    }

    public static Boolean allDifferent(Variable[] i) {
        return false;
    }
}
