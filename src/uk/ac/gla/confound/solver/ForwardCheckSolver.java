package uk.ac.gla.confound.solver;

import uk.ac.gla.confound.NQueens;
import uk.ac.gla.confound.Problem;

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
        }
        return !p.current[j].isEmpty(); // Return false if there has been a domain wipeout for v[j]
    }

    public void undoReductions(int i) {
        // Undo all reductions on domain sets caused by v[i]
        for (Integer j: future[i]) {
            reduction = reductions[j].pop();
            p.current[j].addAll(reduction);
        }
        future[i] = new Stack<>(); // Empty the set of future changes made by i
    }

    public void updatedCurrentDomain(int i) {

        p.current[i].clear();
        p.current[i] = p.domain.copy();

        for (ArrayList<Integer> reduction: reductions[i])
            p.current[i].removeAll(reduction);
    }

    @Override
    public int label(int i) {
        p.consistent = false;
        ArrayList<Integer> dom = (ArrayList<Integer>) p.current[i].clone();

        // Check each value variable[i] *could* be until we have a consistent value or we exhaust all current possibilities
        for (int k = 0; k < dom.size() && !p.consistent; k++) {
            p.variables[i].value = dom.get(k);
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

    public static void main(String... args) {
        int n = 0;

        if (args.length > 1 && args[1].startsWith("-n=")) {
            n = new Scanner(args[1]).nextInt();
        } else {
            System.out.println("Usage: Solver.java -n=[NUM]");
        }


        Problem nQueens = new NQueens(n);
        Solver solver = new ForwardCheckSolver(nQueens);
        solver.solve();
        solver.report(nQueens);
    }

}
