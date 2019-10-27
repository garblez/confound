package uk.ac.gla.confound;

import java.util.Arrays;

public class BacktrackSolver {

    Problem p;

    public void solve(Problem p)
    {
        this.p = p;
        p.status = Status.UNKNOWN;
        p.consistent = true;

        int i = 1;

        while (p.status == Status.UNKNOWN) {

            if (p.consistent)
                i = label(i);
            else
                i = unlabel(i);

            if (i > p.numVariables)
                p.status = Status.SOLUTION;
            else if (i == 0)
                p.status = Status.IMPOSSIBLE;
        }
    }


    public int label(int i)
    {
        p.consistent = false;

        // Check each value variable[i] *could* be until we have a consistent value or we exhaust all current possibilities
        for (int j = 0; j < p.currentDomain[i].size() && !p.consistent; j++) {
            p.variables[i] = (Integer)p.currentDomain[i].get(j);
            p.consistent = true;
            // Run through all previously chosen variables and check if they are all consistent with the current candidate
            // variable[i]
            for (int h = 1; h < i && p.consistent; h++) {
                //consistent = check(i, h);    // Check that the constraint between variable[i] and variable[h] holds

                // Remove value from candidates on constraint failure
                if (!(p.consistent = check(i, h)))
                    p.currentDomain[i-1].remove((Integer)p.variables[i]);
            }
        }
        if (p.consistent)
            return i + 1;
        else
            return i;
    }


    public int unlabel(int i)
    {
        int h = i - 1;
        // Rather than store any domain set for the fake variable, we assign the domain as a null pointer and just
        // check for when we try to unlabel the first possible variable
        if (h == 0)
            return h;
        p.currentDomain[i].addAll(Arrays.asList(p.domain.clone()));
        p.currentDomain[h].remove((Integer)p.variables[h]);
        p.consistent = !p.currentDomain[h].isEmpty();
        return h;
    }

    public boolean check(int i, int h)
    {
        if (p.constraints[i-1].get(h-1))
            return p.variables[i] != p.variables[h] && Math.abs(p.variables[i] - p.variables[h]) != Math.abs(i - h);
        return true; // No constraint between i and h so any value either hold works with the other.
    }


    public String solution()
    {
        StringBuilder s = new StringBuilder();
        s.append("Solution[");
        if (this.p.status == Status.IMPOSSIBLE){
            s.append("]");
            return s.toString();
        }
        for (int i = 1; i < this.p.variables.length-1; i++)
            s.append(this.p.variables[i] + ", ");
        s.append(this.p.variables[this.p.variables.length-1]+"]");
        return s.toString();
    }

}
