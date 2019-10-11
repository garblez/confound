package uk.ac.gla.confound;

import java.util.HashMap;

// We map variables involved in the constraint to the constraint requirements:
// we can therefore check if the group of variables satisfy the constraint.

// All constraints in the problem will be represented by a
//          HashMap<Variable, Constraint>
// in future and the constraint concerning the Variable may be called upon to return all other variables involved.
public class Constraint extends HashMap {
    public static Constraint where(Variable... variables) {}
}
