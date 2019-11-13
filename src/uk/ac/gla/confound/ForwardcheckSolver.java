package uk.ac.gla.confound;

import java.util.*;

public class ForwardcheckSolver extends Solver {

    // Members particular to explicit forward-checking
    ArrayList<Integer> reduction;
    Stack<ArrayList<Integer>>[] reductions;  // The redacted domain sets in LIFO order for each variable
    Stack<Integer>[] futureFC;
    Stack<Integer>[] pastFC;  // TODO: Not necessary for explicit forward checking but useful for backjumping varieties

    public ForwardcheckSolver(Problem p) {
        super(p);
        reduction = new ArrayList();
        reductions = new Stack[p.numVariables];
        futureFC = new Stack[p.numVariables];
        pastFC = new Stack[p.numVariables];
        for (int i = 0; i < p.numVariables; i++) {
            reductions[i] = new Stack<ArrayList<Integer>>();
            futureFC[i] = new Stack<>();
            pastFC[i] = new Stack<>();
            pastFC[i].push(0);
        }
    }

    @Override
    public int label(int i) {
        p.consistent = false;

        // Check each value variable[i] *could* be until we have a consistent value or we exhaust all current possibilities
        for (int j = 0; j < p.currentDomain[i].size() && !p.consistent; j++) {
            p.variables[i] = (Integer)p.currentDomain[i].get(j);
            p.consistent = true;
            // Run through all future variable value candidates and check if they are all consistent with the current candidate
            // variable[i]
            for (int f = i+1; f < p.numVariables && p.consistent; f++) {
                if (!(p.consistent = checkForward(i,f))) {
                    p.currentDomain[i].remove(p.variables[i]);
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
        int h = i - 1;
        // Rather than store any domain set for the fake variable, we assign the domain as a null pointer and just
        // check for when we try to unlabel the first possible variable
        if (h == 0)
            return h;
        undoReductions(h); // TODO: Determine whether this should go before or after `if (h==0) return h;`
        updatedCurrentDomain(i);
        p.currentDomain[h].remove((Integer)p.variables[h]);
        p.consistent = !p.currentDomain[h].isEmpty();
        return h;
    }



    // Methods particular to explicit forward-checking



    boolean checkForward(int i, int j) {
        reduction = new ArrayList<Integer>();   // reduction <- nil

        for (int a = 0; a < p.currentDomain[j].size(); a++) {
            p.variables[j] = p.currentDomain[j].get(a);
            if (!check(i,j))
                reduction.add(p.variables[j]);

            if (!reduction.isEmpty()) {
                p.currentDomain[j] = setDifference(p.currentDomain[j], reduction);
                reductions[j].push(reduction);
                futureFC[i].push(j);
                pastFC[j].push(i);
            }
        }
        return !p.currentDomain[j].isEmpty();
    }

    void undoReductions(int i) {
    }

    void updatedCurrentDomain(int i) {

    }

    // Return a collection of values present in domain or reduction but not in both
    ArrayList<Integer> setDifference(ArrayList domain, ArrayList reduction) {
        ArrayList<Integer> difference = new ArrayList<Integer>(domain);
        difference.removeIf(reduction::contains);
        return difference;
    }
}
