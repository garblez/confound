package uk.ac.gla.confound.marktwo;


import java.util.function.Function;

// We map variables involved in the constraint to the constraint requirements:
// we can therefore check if the group of variables satisfy the constraint.

public class Constraint {
    public Function<Variable[], Boolean> condition;
    public Variable[] variable;

    public boolean check() {
        return condition.apply(variable);
    }

    public boolean check(int i, int h) {
        System.out.println("<"+i+","+h+">");
        return variable[i].value != variable[h].value &&
                Math.abs(variable[i-1].value - variable[h-1].value) != Math.abs(variable[i-1].index - variable[h-1].index);
    }

    public Constraint(Variable... variable) {
        this.variable = variable;
/*
        for (int i = 0; i < variable.length; i++) {
            for (int j = 0; j < variable.length; j++) {
                if (!variable[i].equals(variable[j])) {
                    // Update the constraint tables such that this constraint between all variable arguments is recorded
                    // within each variable
                    System.out.println("Adding mutual constraints between: "+variable[i].index+", "+variable[j].index);
                    variable[i].addConstraintWith(variable[j], this);
                    variable[j].addConstraintWith(variable[i], this);
                }
            }
        }
 */
        variable[0].addConstraintWith(variable[1], this);
    }

    public Constraint where(Function<Variable[], Boolean> condition) {
        this.condition = condition;
        return this;
    }

}
