package uk.ac.gla.confound.examples.graphColouring;

import uk.ac.gla.confound.constraint.NeqConstraint;
import uk.ac.gla.confound.problem.Domain;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.*;

public class GraphColouring extends Problem {
    private String[] colours = new String[]{"red", "blue", "yellow"};
    private String[] countries = new String[]{"albania", "bulgaria", "china", "denmark", "england"};

    public GraphColouring() {
        super(new Domain(0,2), 5);
        variables[1].setName("albania");
        variables[2].setName("bulgaria");
        variables[3].setName("china");
        variables[4].setName("denmark");
        variables[5].setName("england");


        //constraints.addBoth(1,2, new NeqConstraint(variables[1], variables[2]));
        //constraints.addBoth(1,3, new NeqConstraint(variables[1], variables[3]));
        //constraints.addBoth(2,3, new NeqConstraint(variables[2], variables[3]));
        //constraints.addBoth(1,2, new NeqConstraint(variables[1], variables[2]));
        //constraints.addBoth(3,4, new NeqConstraint(variables[3], variables[4]));


        // Between Albania and China
        constraints.addBoth(1, 3, new NeqConstraint(variables[1], variables[3]));
        // Between Albania and Denmark
        constraints.addBoth(1, 4, new NeqConstraint(variables[1], variables[4]));
        // Between Albania and England
        constraints.addBoth(1, 5, new NeqConstraint(variables[1], variables[5]));
        // Between Denmark and England
        constraints.addBoth(4, 5, new NeqConstraint(variables[4], variables[5]));
        // Between Denmark and Bulgaria
        constraints.addBoth(2, 4, new NeqConstraint(variables[2], variables[4]));
        // Between Bulgaria and England
        constraints.addBoth(2, 5, new NeqConstraint(variables[2], variables[5]));



    }


    @Override
    public void print(int x) {
        int[] sol = solutions.get(x);
        System.out.println("-------------");
        for (int i = 0; i < sol.length; i++ ) {
            System.out.println(variables[i+1].name +"="+sol[i]);
        }
        System.out.println("-------------");
    }

    public static void main(String... args) {
        Problem p = new GraphColouring();
        Solver dbt = new BacktrackSolver(p);
        dbt.solve();
        dbt.report();
    }
}
