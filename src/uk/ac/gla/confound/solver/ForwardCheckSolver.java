package uk.ac.gla.confound.solver;

import uk.ac.gla.confound.problem.Problem;

import java.util.ArrayList;
import java.util.Stack;


/**
 * ForwardChecking solver
 * This solver operates similarly to BacktrackSolver but tracks and eliminates values from the current domain of
 * future variables based on the value of the current variable in the search - in a sense, checking forward.
 * Stacks track which domain reductions occur and due to whom in a last-caused order so that domains can be repaired when
 * a previous variable's value is shown to be incongruent.
 */
public class ForwardCheckSolver extends Solver {

    private Stack<Integer>[] future;
    private Stack<ArrayList<Integer>>[] reductions;
    private ArrayList<Integer> reduction;

    public ForwardCheckSolver(Problem p) {
        super(p);
        NAME = "ForwardCheckSolver";

        future = new Stack[p.numVariables+1];
        reductions = new Stack[p.numVariables+1];

        for (int i = 0; i < p.numVariables+1; i++) {
            future[i] = new Stack<>();
            reductions[i] = new Stack<>();
        }

    }


    /**
     * For the current variable, remove the conflicting variables in the next variable along
     * @param i The current variable whose value we check forward from
     * @param j The next variable whose domain we reduce because of i
     * @return
     */
    public boolean checkForward(int i, int j) {
        reduction = new ArrayList<>();

        // Collect all conflicting values in j's current domain
        for (int k = 0; k < p.variables[j].currentDomain.size(); k++) {
            p.variables[j].value = p.variables[j].currentDomain.get(k);
            if (!p.check(i, j)) reduction.add(p.variables[j].value);
        }
        // Apply filtering on j's current domain
        if (!reduction.isEmpty()) {
            p.variables[j].currentDomain.removeAll(reduction);
            // Record the changes made
            reductions[j].push(reduction);
            future[i].push(j);
        }
        // Return false if there has been a domain wipe-out for v[j]
        return !p.variables[j].currentDomain.isEmpty();
    }

    /**
     * Undo reductions on variable domains as a result of the value on variable i
     * @param i The index of the variable which caused the domain reductions we wish to undo
     */
    public void undoReductions(int i) {
        // Undo all reductions on domain sets caused by v[i]
        for (Integer j: future[i]) {
            reduction = reductions[j].pop();
            p.variables[j].currentDomain.addAll(reduction);
        }
        future[i] = new Stack<>(); // Empty the set of future changes made by i
    }

    /**
     * Reset a variable's current domain to its original state, applying any reductions to it as a
     * result of the values of other variables
     * @param i The index of the subject variable
     */
    public void updatedCurrentDomain(int i) {
        p.variables[i].currentDomain.clear();
        p.variables[i].currentDomain = p.variables[i].domain.copy();

        for (ArrayList<Integer> reduction: reductions[i])
            p.variables[i].currentDomain.removeAll(reduction);
    }

    /**
     * Try to choose a value for the current variable i by forward checking: for each remaining value in i's current domain,
     * attempt to check if it excludes values from future variables by constraint breaking and record those changes alongside
     * the current variable and its value as a culprit.
     * @param i The index of the current variable
     * @return The index of the next variable or i if a domain wipe-out occurs
     */
    @Override
    public int label(int i) {
        p.consistent = false;

        // Check each value variable[i] *could* be until we have a consistent value or we exhaust all current possibilities
        for (int k = p.variables[i].currentDomain.size() - 1; k > -1  && !p.consistent; k--) {
            p.variables[i].value = p.variables[i].currentDomain.get(k);
            p.consistent = true;

            for (int j = i + 1; j < p.numVariables + 1 && p.consistent; j++) {
                if (!(p.consistent = checkForward(i, j))) {
                    p.variables[i].currentDomain.remove(Integer.valueOf(p.variables[i].value));
                    undoReductions(i);
                }
            }
        }

        if (p.consistent)
            return i + 1;
        else
            return i;
    }

    /**
     * Variable i has no possible values to choose so the previous variable was informed incorrectly on how to
     * limit its working domain. Undo those limitations and update variable i's current domain.
     * Remove the value of the previous variable from its own domain as no consequent choice for i could
     * be made. If this results in a domain wipe-out for the previous variable, allow it to backtrack further.
     * @param i The index of the current variable.
     * @return The index of the previous variable (i-1)
     */
    @Override
    public int unlabel(int i) {
        int h = i-1;
        undoReductions(h);
        updatedCurrentDomain(i);
        p.variables[h].currentDomain.remove(Integer.valueOf(p.variables[h].value));
        p.consistent = !p.variables[h].currentDomain.isEmpty();
        return h;
    }

}
