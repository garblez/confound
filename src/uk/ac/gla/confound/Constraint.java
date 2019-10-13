package uk.ac.gla.confound;

import com.sun.org.apache.xpath.internal.operations.Variable;

import java.util.HashMap;
import java.util.function.Function;

// We map variables involved in the constraint to the constraint requirements:
// we can therefore check if the group of variables satisfy the constraint.

// All constraints in the problem will be represented by a
//          HashMap<Variable, Constraint>
// in future and the constraint concerning the Variable may be called upon to return all other variables involved.
public class Constraint {
    public Function<Integer[], Boolean> condition;
    public Integer[] variables;

    public boolean check() {
        return condition.apply(variables);
    }

    public Constraint(Function<Integer[], Boolean> fn, Integer... variables) {
        this.variables = variables;
        this.condition = fn;
    }

    public static void main(String[] args){
        Integer v = 3;
        Integer u = 4;
        Constraint c = new Constraint(x-> x[0] + x[1] < 3, v, u);
        System.out.println(c.check());
    }
}
