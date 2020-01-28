package uk.ac.gla.confound;

public class QueenConstraint extends BaseConstraint {

    public QueenConstraint(Variable[] vars) {
        super(vars);
    }

    @Override
    public boolean check(int i, int j) {
        return !variables[i].equals(variables[j]) && Math.abs(variables[i].value - variables[j].value) != Math.abs(i - j);
    }
}
