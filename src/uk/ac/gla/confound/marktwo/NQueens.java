package uk.ac.gla.confound.marktwo;

import java.util.HashMap;
import java.util.Map;

public class NQueens extends Problem {
    public NQueens(int numVars) {
        // Create the space to store constraints, variables and solutions for the problem
        super(numVars);


        for (int i = 1; i < numVars+1; i++)
            this.variables[i] = new Variable(i, 0).with(new Domain(0, numVars));

        // Introduce not-equal constraints between each two different variables
        for (int i = 1; i < numVars+1; i++) {
            for (int j = 1; j < numVars+1; j++) {
                System.out.println("> "+ i + ", " + j);
                if (i != j) {
                    System.out.println(">> "+ i + ", " + j);
                    constraints.add(new Constraint(this.variables[i], this.variables[j])
                            .where(vars -> {
                                // v_i != v_j and |v_i - v_j| != |i-j|
                                return vars[1] != vars[0] && Math.abs(vars[1].value - vars[0].value) != Math.abs(vars[1].index - vars[0].index);
                            }));
                }
            }
            System.out.println("Constraint set "+i+": "+constraints.size());
        }
    }

    @Override
    public boolean check(int i, int h) {
        System.out.println("("+i+", "+h+")");
        if (i != h && variables[i].constraints.containsKey(h))
            return variables[i].constraints.get(h).check(i,h);
        return true;
    }
}
