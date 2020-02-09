package uk.ac.gla.confound.solver;

import uk.ac.gla.confound.problem.Problem;

public class BackjumpSolver extends Solver {

    public int[] maxCheck;

    public BackjumpSolver(Problem p) {
        super(p);
        NAME = "BackjumpSolver";
        maxCheck = new int[p.numVariables];
        for (int i = 0; i < p.numVariables; i++)
            maxCheck[i] = 0;
    }

    @Override
    public int label(int i) {
        p.consistent = false;
        // Check each value variable[i] *could* be until we have a consistent value or we exhaust all current possibilities
        for (int j = 0; j < p.variables[i].currentDomain.size() && !p.consistent; j++) {
            p.variables[i].value = p.variables[i].currentDomain.get(j);

            p.consistent = true;
            // Run through all previously chosen variables and check if they are all consistent with the current candidate
            // variable[i]
            for (int h = 1; h < i && p.consistent; h++) {

                maxCheck[i-1] = Math.max(maxCheck[i-1], h);

                // Remove value from candidates on constraint failure
                if (!(p.consistent = p.check(i, h))) {
                    p.variables[i].currentDomain.remove(Integer.valueOf(p.variables[i-1].value));
                }
            }
        }
        if (p.consistent)
            return i + 1;
        else
            return i;
    }

    @Override
    public int unlabel(int i) {
        int h = maxCheck[i-1];
        // Rather than store any domain set for the fake variable, we assign the domain as a null pointer and just
        // check for when we try to unlabel the first possible variable


        for (int j = h+1; j < i; j++) {
            maxCheck[j-1] = 0;
            p.variables[j].currentDomain = p.domain.copy();
        }
        p.variables[i].currentDomain = p.domain.copy();
        p.variables[h].currentDomain.remove(Integer.valueOf(p.variables[h].value));
        p.consistent = !p.variables[h].currentDomain.isEmpty();

        return h;
    }

}
