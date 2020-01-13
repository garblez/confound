package uk.ac.gla.confound;

import java.util.*;

public class ForwardChecker extends Solver {

    public ForwardChecker(Problem p) {
        super(p);
        future = new Stack[p.numVariables+1];
        past = new Stack[p.numVariables+1];
        reductions = new Stack[p.numVariables+1];

        for (int i = 0; i < p.numVariables+1; i++) {
            future[i] = new Stack<Integer>();
            past[i] = new Stack<Integer>();
            past[i].push(0);
            reductions[i] = new Stack<ArrayList<Integer>>();
        }

    }

    Stack<Integer>[] future;
    Stack<Integer>[] past;
    Stack<ArrayList<Integer>>[] reductions;
    ArrayList<Integer> reduction;

    public boolean checkForward(int i, int j) {
        reduction = new ArrayList<>();

        // Collect all conflicting values in j's current domain
        for (int k = 0; k < p.current[j].size(); k++) {
            p.variables[j].value = p.current[j].get(k);
            if (!p.check(i, j)) reduction.add(p.variables[j].value);
        }
        // Apply filtering on j's current domain
        if (!reduction.isEmpty()) {
            p.current[j].removeAll(reduction);
            // Record the changes made
            reductions[j].push(reduction);
            future[i].push(j);
            past[j].push(i);
        }
        return !p.current[j].isEmpty(); // Return false if there has been a domain wipeout for v[j]
    }

    public void undoReductions(int i) {
        // Undo all reductions on domain sets caused by v[i]
        for (Integer j: future[i]) {
            reduction = reductions[j].pop();
            p.current[j].addAll(reduction);
            // Remove duplicate entries
            p.current[j] = new ArrayList<Integer>(new HashSet<Integer>(p.current[j]));
            past[j].pop();
        }
        future[i] = new Stack<>(); // Empty the set of future changes made by i
    }

    public void updatedCurrentDomain(int i) {
        // TODO: currently there is a bug with this particular function when calling p.domain.copy() -- we end up with a duplicate final solution.
        //      Solve this!
        ArrayList<Integer> cDomain = new ArrayList<>();
        for (int j = 0; j < p.numVariables; j++)
            cDomain.add(j);
        p.current[i] = cDomain;
        for (ArrayList<Integer> reduction: reductions[i])
            p.current[i].removeAll(reduction);
    }

    @Override
    public int label(int i) {
        p.consistent = false;

        // Check each value variable[i] *could* be until we have a consistent value or we exhaust all current possibilities
        for (int k = 0; k < p.current[i].size() && !p.consistent; k++) {
            p.variables[i].value = p.current[i].get(k);
            p.consistent = true;

            for (int j = i+1; j < p.numVariables+1 && p.consistent; j++) {
                if (!(p.consistent = checkForward(i, j))){
                    p.current[i].remove(Integer.valueOf(p.variables[i].value));
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
        p.current[h].remove(Integer.valueOf(p.variables[h].value));
        p.consistent = !p.current[h].isEmpty();
        return h;
    }
}
