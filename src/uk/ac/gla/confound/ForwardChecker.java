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
        //Integer nextVariable;
        // Collect all conflicting values in j's current domain
        for (int k = 0; k < p.currentDomain[j].size(); k++) {
            p.variables[j] = p.currentDomain[j].get(k);
            if (!p.check(i, j)) reduction.add(p.variables[j]);
        }
        // Apply filtering on j's current domain
        if (!reduction.isEmpty()) {
            p.currentDomain[j].removeAll(reduction);
            // Record the changes made
            reductions[j].push(reduction);
            future[i].push(j);
            past[j].push(i);
        }
        return !p.currentDomain[j].isEmpty(); // Return false if there has been a domain wipeout for v[j]
    }

    public void undoReductions(int i) {
        // Undo all reductions on domain sets caused by v[i]
        for (Integer j: future[i]) {
            reduction = reductions[j].pop();
            p.currentDomain[j].addAll(reduction);
            // Remove duplicate entries
            p.currentDomain[j] = new ArrayList<Integer>(new HashSet<Integer>(p.currentDomain[j]));
            past[j].pop();
        }
        future[i] = new Stack<>(); // Empty the set of future changes made by i
    }

    public void updatedCurrentDomain(int i) {
        p.currentDomain[i] = new ArrayList<Integer>(Arrays.asList(p.domain.clone()));
        for (ArrayList<Integer> reduction: reductions[i])
            p.currentDomain[i].removeAll(reduction);
    }

    @Override
    public int label(int i) {
        p.consistent = false;

        // Check each value variable[i] *could* be until we have a consistent value or we exhaust all current possibilities
        for (int k = 0; k < p.currentDomain[i].size() && !p.consistent; k++) {
            p.variables[i] = (Integer)p.currentDomain[i].get(k);
            p.consistent = true;

            for (int j = i+1; j < p.numVariables+1 && p.consistent; j++) {
                if (!(p.consistent = checkForward(i, j))){
                    p.currentDomain[i].remove((Integer)p.variables[i]);
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
        if (h == 0)
            return h;
        undoReductions(h);
        updatedCurrentDomain(i);
        System.out.print("["+h+"] Remove "+p.variables[h]+" from ");
        for (Integer val: p.currentDomain[h])
            System.out.print(val+", ");
        System.out.println();
        p.currentDomain[h].remove((Integer)p.variables[h]);
        for (Integer val: p.currentDomain[h])
            System.out.print(val+", ");
        System.out.println();
        p.consistent = !p.currentDomain[h].isEmpty();
        return h;
    }
}
