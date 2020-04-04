package uk.ac.gla.confound.examples.crystalMaze;

import uk.ac.gla.confound.constraint.DistanceConstraint;
import uk.ac.gla.confound.constraint.NeqConstraint;
import uk.ac.gla.confound.problem.Domain;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.BacktrackSolver;
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
                    constraints[i][j].add(new NeqConstraint(variables[i], variables[j]));
                }
            }
        }


        // Make sure that neighbours differ by more than 1
        ArrayList<Integer>[] adjLists = new ArrayList[numVariables+1];
        for (int i = 0; i < numVariables+1; i++)
            adjLists[i] = new ArrayList<Integer>();

        adjLists[1].addAll(Arrays.asList(2, 4, 7));
        adjLists[2].addAll(Arrays.asList(1, 4, 5, 3));
        adjLists[3].addAll(Arrays.asList(2, 4, 5, 6));
        adjLists[4].addAll(Arrays.asList(1, 2, 3, 5, 7, 8));
        adjLists[5].addAll(Arrays.asList(2, 3, 4, 6, 7, 8));
        adjLists[6].addAll(Arrays.asList(3, 5, 8));
        adjLists[7].addAll(Arrays.asList(1, 4, 5, 8));
        adjLists[8].addAll(Arrays.asList(4, 5, 6, 7));

        int j;

        for (int i = 1; i <= numVariables; i++) {
            for (int x = 0; x < adjLists[i].size(); x++) {
                j = adjLists[i].get(x);
                constraints[i][j].add(new DistanceConstraint(variables[i], variables[j], DistanceConstraint.Op.NEQ, 1));
            }
        }

    }

    public static void main(String... args) {
        Problem p = new CrystalMaze(8);

        Solver s = new BacktrackSolver(p);

        s.solveAll();
        s.report(p);
    }


}
