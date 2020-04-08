package uk.ac.gla.confound.examples.basic;

import uk.ac.gla.confound.constraint.Constraint;
import uk.ac.gla.confound.problem.Domain;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.*;

public class AllDifferent extends Problem {
    public AllDifferent(int n) {
        super(new Domain(1, n), n);
        Constraint.allDiff(constraints, variables);
    }

    public static void main(String... args) {
        Problem p = new AllDifferent(3);
        Solver s = new DynamicBacktrackSolver(p);
        s.solve();
        s.report(p);

    }

}
