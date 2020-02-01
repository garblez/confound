package uk.ac.gla.confound.constraint;


import uk.ac.gla.confound.problem.Problem;

public abstract class Constraint {
    public Problem p;
    public int i, j;

    public Constraint(Problem p, int i, int j) {
        this.p = p;
        this.i = i;
        this.j = j;
    }

    public boolean check() {
        return true;
    }
}
