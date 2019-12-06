package uk.ac.gla.confound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Stack;

public class FCSolver extends Solver {
    // Note that variable indexes must account for the fake variable at i=0 with an offset
    // of -1


    // For every variable in the problem, we have a sequence of sequences of reductions
    Stack<ArrayList<Integer>>[]  reductions;

    // For each variable in the problem, refer to in order of last accessed
    // the subset of future values the current variable is checked against
    Stack<Integer>[] future;

    // For each variable in the problem, refer to in order of last accessed
    // the previous variables we checked the current variable against.
    Stack<Integer>[] past;

    // Sequence of values disallowed in the current variable's current domain due to
    // instantiation of a past variable.
    ArrayList<Integer> reduction;

    public FCSolver(Problem p)
    {
        super(p);
        reductions = new Stack[p.numVariables+1];
        future = new Stack[p.numVariables+1];
        past = new Stack[p.numVariables+1];


        for (int i = 1; i <= p.numVariables; i++) {
            reductions[i] = new Stack<>();
            future[i] = new Stack<>();
            past[i] = new Stack<>();
        }
        reductions[0] = null;
        future[0] = null;
        past[0] = null;

        reduction = new ArrayList<>();
    }


    public boolean checkForward(int i, int j)
    {
        reduction = new ArrayList<>();
        for (int x = 0; x < p.currentDomain[j].size(); x++) {
            p.variables[j] = p.currentDomain[j].get(x);
            if (!p.check(i,j))
                reduction.add(p.variables[j]);
        }
        if (!reduction.isEmpty()) {
            p.currentDomain[j] = setDifference(p.currentDomain[j], reduction);
            reductions[j].add(reduction);
            future[i].push(j);
            past[j].push(i);
        }
        return !p.currentDomain[j].isEmpty();
    }

    // Undo reductions for variable i
    public void undo(int i)
    {

        for (Integer j: future[i]) {
            if (reductions[j].isEmpty())
                break;
            reduction = reductions[j].pop();
            p.currentDomain[j] = setUnion(p.currentDomain[j], reduction);
            past[j].pop();
        }
        future[i] = new Stack<>();
    }

    // Update the current domain of variable i
    public void update(int i)
    {
        p.currentDomain[i] = new ArrayList<Integer>(Arrays.asList(p.domain));
        for (int k = 0; k < reductions[i-1].size(); k++) {
            reduction = reductions[i-1].get(k);
            p.currentDomain[i] = setDifference(p.currentDomain[i], reduction);
        }
    }

    @Override
    public int label(int i) {
        p.consistent = false;
        for (int k = 0; k < p.currentDomain[i].size() && !p.consistent; k++) {
            p.consistent = true;
            for (int j = i + 1; j < p.numVariables && p.consistent; j++) {
                if (!(p.consistent = checkForward(i,j))) {
                    p.currentDomain[i].remove((Integer)p.variables[i]);
                    undo(i);
                }
            }
        }
        if (p.consistent)
            return i+1;
        return i;
    }

    @Override
    public int unlabel(int i) {
        int h = i - 1;
        undo(h);
        update(i);
        p.currentDomain[h].remove((Integer)p.variables[h]);
        p.consistent = !p.currentDomain[h].isEmpty();
        return h;
    }

    // Return a collection of values present in domain or reduction but not in both
    ArrayList<Integer> setDifference(ArrayList domain, Collection reduction) {
        ArrayList<Integer> difference = new ArrayList<Integer>(domain);
        difference.removeIf(reduction::contains);
        return difference;
    }

    ArrayList<Integer> setUnion(ArrayList domain, Collection<Integer> reduction) {
        ArrayList<Integer> union = new ArrayList<Integer>(domain);
        for (Integer x: reduction)
            if (!domain.contains(x))
                union.add(x);
        return union;
    }
}
