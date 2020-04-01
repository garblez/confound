package uk.ac.gla.confound.solver;

import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.problem.Variable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DynamicBacktrackSolver extends Solver {
    public ViolationRegister[] violation;

    public DynamicBacktrackSolver(Problem p) {
        super(p);
        NAME = "DynamicBacktrackSolver";
        violation = new ViolationRegister[p.numVariables+1];

        for (int i = 0; i <= p.numVariables; i++)
            violation[i] = new ViolationRegister(p.variables[i]);
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
        violation[i].updateCurrentDomain();
        // Check each value variable[i] *could* be until we have a consistent value or we exhaust all current possibilities
        for (int j = 0; j < p.variables[i].currentDomain.size() && !p.consistent; j++) {

            p.variables[i].value = p.variables[i].currentDomain.get(j);


            p.consistent = true;
            // Run through all previously chosen variables and check if they are all consistent with the current candidate
            // variable[i]
            for (int h = 1; h < i && p.consistent; h++) {
                // Remove value from candidates on constraint failure
                if (!(p.consistent = p.check(i, h)) && !violation[i].forbiddenValues().contains(p.variables[i].value)) {
                    violation[i].addViolation(p.variables[i].value, h);
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
        int h = violation[i].getRecentCulprit();

        // Rather than store any domain set for the fake variable, we assign the domain as a null pointer and just
        // check for when we try to unlabel the first possible variable

        removeExplanationsFor(h);
        violation[h].supposeViolation();
        violation[h].updateCurrentDomain();

        p.consistent = !p.variables[h].currentDomain.isEmpty();

        return h;
    }

    // Remove any and all eliminating explanations involving variable i as a violator
    public void removeExplanationsFor(int i) {
        ArrayList<Integer> removalKeys;
        for (int j = p.numVariables; j > i; j--) {
            removalKeys = new ArrayList<>();
            for (Integer value: violation[j].forbiddenValues()) {
                if (violation[j].rule.get(value).contains(i))
                    removalKeys.add(value);
            }
            for (Integer key: removalKeys) {
                violation[j].removeViolation(key);
            }
        }
    }
}
