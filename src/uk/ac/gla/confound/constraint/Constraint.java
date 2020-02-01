package uk.ac.gla.confound.constraint;


import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.problem.Variable;

public abstract class Constraint {
    public Variable var0;
    public Variable var1;

    public Constraint(){

    }

    public Constraint(Problem p, int i, int j) {
        var0 = p.variables[i];
        var1 = p.variables[j];
    }

    public boolean check() {
        return true;
    }
}
