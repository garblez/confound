package uk.ac.gla.confound.examples;

import uk.ac.gla.confound.constraint.NeqConstraint;
import uk.ac.gla.confound.constraint.NotConstraint;
import uk.ac.gla.confound.problem.Domain;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.problem.Variable;

public class Sendmory extends Problem {
    public Sendmory() {
        super(new Domain(0,9), 8);
        for (int i = 0; i< 8; i++)
            variables[i] = new Variable(new Domain(0,9), i);
        /*
        0 1 2 3 4 5 6 7
        D E M N O R S Y
         */

        constraints[2][2].add(new NotConstraint(variables[2], 0));
        constraints[7][7].add(new NotConstraint(variables[7], 0));

    }
}
