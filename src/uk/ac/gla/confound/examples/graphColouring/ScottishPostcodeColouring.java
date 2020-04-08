package uk.ac.gla.confound.examples.graphColouring;

import uk.ac.gla.confound.constraint.NeqConstraint;
import uk.ac.gla.confound.problem.Domain;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.ForwardCheckSolver;
import uk.ac.gla.confound.solver.Solver;

public class ScottishPostcodeColouring extends Problem {
    public ScottishPostcodeColouring() {
        super(new Domain(16),16); // There are 16 postcode areas in scotland
        variables[1].setName("IV");
        variables[2].setName("KW");
        variables[3].setName("AB");
        variables[4].setName("DD");
        variables[5].setName("KY");
        variables[6].setName("PH");
        variables[7].setName("PA");
        variables[8].setName("HS");
        variables[9].setName("G");
        variables[10].setName("TD");
        variables[11].setName("EH");
        variables[12].setName("DG");
        variables[13].setName("KA");
        variables[14].setName("ML");
        variables[15].setName("FK");
        variables[16].setName("ZE");

        constraints.addBoth(14, 9, new NeqConstraint(variables[14], variables[9])); // G - ML
        constraints.addBoth(14, 15, new NeqConstraint(variables[14], variables[15])); // FK - ML
        constraints.addBoth(14, 11, new NeqConstraint(variables[14], variables[11])); // EH - ML
        constraints.addBoth(14, 12, new NeqConstraint(variables[14], variables[12])); // DG - ML
        constraints.addBoth(14, 10, new NeqConstraint(variables[14], variables[10])); // TD - ML
        constraints.addBoth(14, 13, new NeqConstraint(variables[14], variables[13])); // KA - ML
        constraints.addBoth(9, 15, new NeqConstraint(variables[9], variables[15])); // G - FK
        constraints.addBoth(9, 13, new NeqConstraint(variables[9], variables[13])); // G - KA
        constraints.addBoth(9, 7, new NeqConstraint(variables[9], variables[7])); // G - PA
        constraints.addBoth(9, 12, new NeqConstraint(variables[9], variables[12])); // G - DG
        constraints.addBoth(13, 12, new NeqConstraint(variables[13], variables[12])); // KA - DG
        constraints.addBoth(12, 10, new NeqConstraint(variables[12], variables[10])); // DG - TD
        constraints.addBoth(6, 15, new NeqConstraint(variables[6], variables[15])); // PH - FK
        constraints.addBoth(6, 1, new NeqConstraint(variables[6], variables[1])); // PH - IV
        constraints.addBoth(6, 4, new NeqConstraint(variables[6], variables[4])); // PH - DD
        constraints.addBoth(6, 5, new NeqConstraint(variables[6], variables[5])); // PH - KY
        constraints.addBoth(5, 15, new NeqConstraint(variables[5], variables[15])); // KY - FK
        constraints.addBoth(1, 7, new NeqConstraint(variables[1], variables[7])); // IV - PA
        constraints.addBoth(1, 8, new NeqConstraint(variables[1], variables[8])); // IV - HS
        constraints.addBoth(1, 3, new NeqConstraint(variables[1], variables[3])); // IV - AB
        constraints.addBoth(1, 7, new NeqConstraint(variables[1], variables[7])); // IV - PA
        constraints.addBoth(3, 4, new NeqConstraint(variables[3], variables[4])); // AB - DD
        constraints.addBoth(1, 7, new NeqConstraint(variables[1], variables[7])); // IV - PA
        constraints.addBoth(1, 2, new NeqConstraint(variables[1], variables[2])); // IV - KW
        constraints.addBoth(1, 7, new NeqConstraint(variables[1], variables[7])); // IV - PA
        constraints.addBoth(2, 16, new NeqConstraint(variables[2], variables[16])); // KW - ZE


    }

    public static void main(String... args) {
        Problem p = new ScottishPostcodeColouring();
        Solver s = new ForwardCheckSolver(p);
        s.solve(1);
        System.out.println(p.solutions.size());
        s.report(p);
        System.out.println(p.variables[5]);
    }
}
