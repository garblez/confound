package uk.ac.gla.confound.problem;

// Constants are Variables with singleton domains
public class Constant extends Variable {
    public Constant(int value, int index) {
        super(new Domain(value), index);
    }
}
