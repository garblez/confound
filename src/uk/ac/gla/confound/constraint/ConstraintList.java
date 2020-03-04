package uk.ac.gla.confound.constraint;

import java.util.ArrayList;

public class ConstraintList {
    private ArrayList<Constraint> constraints;

    public ConstraintList() {
        constraints = new ArrayList<>();
    }

    public void add(Constraint c) {
        constraints.add(c);
    }

    public void remove(Constraint c) {
        constraints.remove(c);
    }

    public boolean check() {
        for (Constraint c: constraints)
            if (!c.check()) return false; // Quit as soon as a constraint is false
        return true; // All constraints hold true
    }
}
