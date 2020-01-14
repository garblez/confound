package uk.ac.gla.confound;

import java.util.HashSet;
import java.util.Set;

public class ConflictBackjumpSolver extends Solver {

    public ConflictSet conflicts;

    public ConflictBackjumpSolver(Problem p) {
        super(p);
        conflicts = new ConflictSet(p.numVariables);
    }

    @Override
    public int label(int i) {
        p.consistent = false;
        // Check each value variable[i] *could* be until we have a consistent value or we exhaust all current possibilities
        for (int j = 0; j < p.current[i].size() && !p.consistent; j++) {
            p.variables[i].value = p.current[i].get(j);

            p.consistent = true;
            // Run through all previously chosen variables and check if they are all consistent with the current candidate
            // variable[i]
            for (int h = 1; h < i && p.consistent; h++) {
                // Remove value from candidates on constraint failure
                if (!(p.consistent = p.check(i, h))) {
                    conflicts.addTo(i,h);
                    p.current[i-1].remove(Integer.valueOf(p.variables[i-1].value));
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
        int h = conflicts.getMaxValue(i);
        conflicts.update(h, i);

        for (int j = h+1; j < i; j++)
            p.current[j] = p.domain.copy();

        // Rather than store any domain set for the fake variable, we assign the domain as a null pointer and just
        // check for when we try to unlabel the first possible variable
        p.current[i] = p.domain.copy();
        p.current[h].remove(Integer.valueOf(p.variables[h].value));
        p.consistent = !p.current[h].isEmpty();

        return h;
    }
}
