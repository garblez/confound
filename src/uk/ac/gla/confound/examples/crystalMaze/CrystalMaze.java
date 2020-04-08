package uk.ac.gla.confound.examples.crystalMaze;

import uk.ac.gla.confound.constraint.DistanceConstraint;
import uk.ac.gla.confound.constraint.NeqConstraint;
import uk.ac.gla.confound.problem.Domain;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.BacktrackSolver;
import uk.ac.gla.confound.solver.ForwardCheckSolver;
import uk.ac.gla.confound.solver.Solver;

import java.util.ArrayList;
import java.util.Arrays;

public class CrystalMaze extends Problem {

    public CrystalMaze(int numVariables) {
        /*
          / 1 - 2 \
        0 - 3 * 4 - 5
          \ 6 - 7 /
         */


        super(new Domain(1, 8), 8);

        /* Make all values different */
        for (int i = 1; i <= numVariables; i++) {
            for (int j = 1; j <= numVariables; j++) {
                if (i != j) {
                    constraints.add(i, j, new NeqConstraint(variables[i], variables[j]));
                }
            }
        }

        constraints.addBoth(0, 1, new DistanceConstraint(variables[0], variables[1], ">", 1));
        constraints.addBoth(0, 3, new DistanceConstraint(variables[0], variables[3], ">", 1));
        constraints.addBoth(0, 6, new DistanceConstraint(variables[0], variables[6], ">", 1));
        constraints.addBoth(3, 1, new DistanceConstraint(variables[3], variables[1], ">", 1));
        constraints.addBoth(3, 6, new DistanceConstraint(variables[3], variables[6], ">", 1));
        constraints.addBoth(6, 7, new DistanceConstraint(variables[6], variables[7], ">", 1));
        constraints.addBoth(3, 7, new DistanceConstraint(variables[3], variables[7], ">", 1));
        constraints.addBoth(3, 4, new DistanceConstraint(variables[3], variables[4], ">", 1));
        constraints.addBoth(3, 2, new DistanceConstraint(variables[3], variables[2], ">", 1));
        constraints.addBoth(1, 4, new DistanceConstraint(variables[1], variables[4], ">", 1));
        constraints.addBoth(1, 2, new DistanceConstraint(variables[1], variables[2], ">", 1));
        constraints.addBoth(6, 4, new DistanceConstraint(variables[6], variables[4], ">", 1));
        constraints.addBoth(2, 4, new DistanceConstraint(variables[2], variables[4], ">", 1));
        constraints.addBoth(2, 5, new DistanceConstraint(variables[2], variables[5], ">", 1));
        constraints.addBoth(4, 5, new DistanceConstraint(variables[4], variables[5], ">", 1));
        constraints.addBoth(4, 7, new DistanceConstraint(variables[4], variables[7], ">", 1));
        constraints.addBoth(5, 7, new DistanceConstraint(variables[5], variables[7], ">", 1));
    }

    public static void main(String... args) {
        Problem p = new CrystalMaze(8);

        Solver s = new ForwardCheckSolver(p);

        s.solve();
        s.report(p);
    }


}
