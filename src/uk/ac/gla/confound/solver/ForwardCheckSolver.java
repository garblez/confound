package uk.ac.gla.confound.solver;

import uk.ac.gla.confound.problem.Problem;

import java.util.*;

public class ForwardCheckSolver extends Solver {

    public ForwardCheckSolver(Problem p) {
        super(p);
        NAME = "ForwardCheckSolver";

        future = new Stack[p.numVariables+1];
        reductions = new Stack[p.numVariables+1];

        for (int i = 0; i < p.numVariables+1; i++) {
            future[i] = new Stack<Integer>();
            reductions[i] = new Stack<ArrayList<Integer>>();
        }

    }

    Stack<Integer>[] future;
    Stack<ArrayList<Integer>>[] reductions;
    ArrayList<Integer> reduction;

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
        return !p.variables[j].currentDomain.isEmpty(); // Return false if there has been a domain wipeout for v[j]
    }

    public void undoReductions(int i) {
        // Undo all reductions on domain sets caused by v[i]
        for (Integer j: future[i]) {
            reduction = reductions[j].pop();
            p.variables[j].currentDomain.addAll(reduction);
        }
        future[i] = new Stack<>(); // Empty the set of future changes made by i
    }

    public void updatedCurrentDomain(int i) {

        p.variables[i].currentDomain.clear();
        p.variables[i].currentDomain = p.domain.copy();

        for (ArrayList<Integer> reduction: reductions[i])
            p.variables[i].currentDomain.removeAll(reduction);
    }

    @Override
    public int label(int i) {
        p.consistent = false;
        ArrayList<Integer> dom = (ArrayList<Integer>) p.variables[i].currentDomain.clone();

        // Check each value variable[i] *could* be until we have a consistent value or we exhaust all current possibilities
        for (int k = 0; k < dom.size() && !p.consistent; k++) {
            p.variables[i].value = dom.get(k);
            p.consistent = true;

            for (int j = i+1; j < p.numVariables+1 && p.consistent; j++) {
                if (!(p.consistent = checkForward(i, j))){
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
