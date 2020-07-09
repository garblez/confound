package uk.ac.gla.confound.solver;

import uk.ac.gla.confound.problem.Problem;

/**
 * The simple BacktrackSolver
 * The label and unlabel operations only work to advance ahead and retreat back by 1 variable. It is the simplest
 * solver within the library.
 */
public class BacktrackSolver extends Solver {

    public BacktrackSolver(Problem p) {
        super(p);
        NAME = "BacktrackSolver";
    }

    /**
     * Label takes in a variable index and searches through that variable's current domain until a value consistent with
     * all preceding variables and their constraints is found or all possibilities are exhausted.
     * @param i The index of the variable to check
     * @return The succeeding variable's index if a solution is found, otherwise the current variable index
     */
    public int label(int i)
    {
        p.consistent = false;
        // Check each value variable[i] *could* be until we have a consistent value or we exhaust all current possibilities
        for (int j = p.variables[i].currentDomain.size() - 1; j > -1  && !p.consistent; j--) {
            p.variables[i].value = p.variables[i].currentDomain.get(j);


            p.consistent = true;
            // Run through all previously chosen variables and check if they are all consistent with the current candidate
            // variable[i]
            for (int h = 1; h < i && p.consistent; h++) {
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

    /**
     * Unlabel is called when the current solution thus far is inconsistent by the introduction of variable i's value.
     * The current domain of variable i is reset to the original, full domain and the current domain of the preceding
     * variable has the value of the preceding variable removed from it. If this causes the current domain of the
     * preceding variable to become empty then the overall solution is still inconsistent.
     * @param i The index of the current variable to check
     * @return The index of the preceding variable
     */
    public int unlabel(int i)
    {
        int h = i - 1;
        // Rather than store any domain set for the fake variable, we assign the domain as a null pointer and just
        // check for when we try to unlabel the first possible variable
        p.variables[i].currentDomain = p.variables[i].domain.copy();
        p.variables[h].currentDomain.remove(Integer.valueOf(p.variables[h].value));
        p.consistent = !p.variables[h].currentDomain.isEmpty();

        return h;
    }




}
