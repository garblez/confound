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

    public Constraint(Integer... variables) {
        this.variables = variables;
    }

    public Constraint where(Function<Integer[], Boolean> condition) {
        this.condition = condition;
        return this;
    }

    public static void main(String[] args){
        Integer variable0 = 3;
        Integer variable1 = -3;
        Constraint c = new Constraint(variable0, variable1).where(var -> var[0] + var[1] < 3);
        System.out.println(c.check());
        Integer variable2 = 0;

        Constraint e =  new Constraint(variable0, variable1, variable2).where(var -> allDifferent(var));

    }

    public static Boolean allDifferent(Integer[] i) {
        return false;
    }
}
