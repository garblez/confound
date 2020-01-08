package uk.ac.gla.confound.marktwo.trailing;

import uk.ac.gla.confound.marktwo.Problem;
import uk.ac.gla.confound.marktwo.Solver;
import uk.ac.gla.confound.marktwo.Variable;


public class BacktrackSolver extends Solver {
    public BacktrackSolver(Problem p) {
        super(p);
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
        // TODO: NullPointerException occurs here when i = numVars. Address indexing issues involved with having a dummy variable at i=0
        for (int j = 0; j < p.variables[i].currentDomain.size() && !p.consistent; j++) {
            p.variables[i].setValue(p.variables[i].currentDomain.get(j));
            p.consistent = true;
            // Run through all previously chosen variables and check if they are all consistent with the current candidate
            // variable[i]
            for (int h = 1; h < i && p.consistent; h++) {
                // Remove value from candidates on constraint failure
                if (!(p.consistent = p.check(i, h)))
                    p.variables[i].currentDomain.remove(p.variables[i].value);
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
        if (h == 0)
            return h;
        p.variables[i].currentDomain = p.variables[i].domain.clone();
        p.variables[h].currentDomain.values.remove((Integer)p.variables[h].value);
        p.consistent = !p.variables[h].currentDomain.isEmpty();
        return h;
    }
}
