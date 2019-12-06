package uk.ac.gla.confound;


public class NQueens extends Problem {
    public NQueens(int numVars) {
        super(numVars);
        /*
            Constraint?
                v0  v1  v2  v3  ...
            v0  0   1   1   1
            v1  1   0   1   1
            v2  1   1   0   1
            v3  1   1   1   0
            .
            .
         */
        for (int i = 0; i < this.numVariables; i++) {
            for (int j = 0; j < this.numVariables; j++) {
                constraints[i].set(j, i != j);  // Let there be a constraint between all different variables
            }
        }
    }

    /**
     * Check tells if the constraint between variable i and variable h holds given their current values.
     * Right now, this is just one condition for all constraints: later, we may be able to map variable sets to
     * constraints and check those constraint's individual conditions.
     * @param i The current variable's index
     * @param h The preceding variable's index
     * @return  true if the constraint between variable i and variable h holds otherwise false
     */
    public boolean check(int i, int h)
    {
        if (constraints[i-1].get(h-1))
            return variables[i] != variables[h] && Math.abs(variables[i] - variables[h]) != Math.abs(i - h);
        return true; // No constraint between i and h so any value either hold works with the other.
    }


}
