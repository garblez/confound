package uk.ac.gla.confound.examples.graphColouring;

import uk.ac.gla.confound.constraint.NeqConstraint;
import uk.ac.gla.confound.problem.Domain;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.solver.ForwardCheckSolver;
import uk.ac.gla.confound.solver.Solver;

public class SmallGraph extends Problem {
    public SmallGraph() {
        super(new Domain(0,2), 4);
        variables[1].setName("a");
        variables[2].setName("b");
        variables[3].setName("c");
        variables[4].setName("d");

        constraints.addBoth(1, 2, new NeqConstraint(variables[1], variables[2]));
        constraints.addBoth(1,3, new NeqConstraint(variables[1], variables[3]));
        constraints.addBoth(2, 3, new NeqConstraint(variables[2], variables[3]));
        constraints.addBoth(3, 4, new NeqConstraint(variables[3], variables[4]));
    }

    enum Colour {
        R, G, B
    }

    Colour colourGet(int c) {
        Colour col;
        switch (c) {
            case 0: col = Colour.R; break;
            case 1: col = Colour.G; break;
            case 2:
            default:
                col = Colour.B;
        }
        return col;
    }

    @Override
    public void print(int x) {
        int[] sln = solutions.get(x);
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(colourGet(sln[0]));
        sb.append("\\\n| ");
        sb.append(colourGet(sln[2]));
        sb.append("-");
        sb.append(colourGet(sln[3]));
        sb.append("\n");
        sb.append(colourGet(sln[1]));
        sb.append("/");
        System.out.println(sb.toString());
    }

    public static void main(String... args) {
        Problem p = new SmallGraph();
        Solver s = new ForwardCheckSolver(p);
        s.solve();
        s.report();
    }
}
