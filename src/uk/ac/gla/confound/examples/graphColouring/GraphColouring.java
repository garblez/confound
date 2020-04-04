package uk.ac.gla.confound.examples.graphColouring;

import uk.ac.gla.confound.constraint.ConstraintList;
import uk.ac.gla.confound.constraint.NeqConstraint;
import uk.ac.gla.confound.problem.Domain;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.BacktrackSolver;
import uk.ac.gla.confound.solver.Solver;

public class GraphColouring extends Problem {
    private String[] colours = new String[]{"red", "blue", "yellow"};
    private String[] countries = new String[]{"albania", "bulgaria", "china", "denmark", "england"};

    public GraphColouring() {
        super(new Domain(0, 2), 5);


        for (int i = 1; i <= this.numVariables; i++) {
            for (int j = 1; j <= this.numVariables; j++) {
                if (i != j) {
                    constraints[i][j] = new ConstraintList();
                }
            }
        }

        // Between Albania and China
        constraints[1][3].add(new NeqConstraint(variables[1], variables[3]));
        constraints[3][1].add(new NeqConstraint(variables[3], variables[1]));
        // Between Albania and Denmark
        constraints[1][4].add(new NeqConstraint(variables[1], variables[4]));
        constraints[4][1].add(new NeqConstraint(variables[4], variables[1]));
        // Between Albania and England
        constraints[1][5].add(new NeqConstraint(variables[1], variables[5]));
        constraints[5][1].add(new NeqConstraint(variables[5], variables[1]));
        // Between Denmark and England
        constraints[4][5].add(new NeqConstraint(variables[4], variables[5]));
        constraints[5][4].add(new NeqConstraint(variables[5], variables[4]));
        // Between Denmark and Bulgaria
        constraints[2][4].add(new NeqConstraint(variables[2], variables[4]));
        constraints[4][2].add(new NeqConstraint(variables[4], variables[2]));
        // Between Bulgaria and England
        constraints[2][5].add(new NeqConstraint(variables[2], variables[5]));
        constraints[5][2].add(new NeqConstraint(variables[5], variables[2]));
    }


    @Override
    public void print(int x) {
        int[] sol = solutions.get(x);
        System.out.println("-------------");
        for (int i = 0; i < 5; i++ ) {
            System.out.println(countries[i] + " = " + colours[sol[i]]);
        }
        System.out.println("-------------");
    }

    public static void main(String... args) {
        Problem p = new GraphColouring();
        Solver dbt = new BacktrackSolver(p);
        dbt.solveAll();
        dbt.report(p);
    }
}
