package uk.ac.gla.confound;

import java.util.ArrayList;
import java.util.Arrays;

public class BacktrackSolver {

    Problem p;
    ArrayList<Integer> previousValues = new ArrayList<>();
    Status status;

    int numIterations = 0;
    int numSolutions = 0;

    public void report(Problem p)
    {
        System.out.println("Status report");
        System.out.println("#Iterations: "+numIterations);
        System.out.println("#Solutions: "+numSolutions);
        System.out.println("Solutions are as follows\n=========================");
        for (int[] arr: p.solutions) {
            System.out.print("[");
            for (int x : arr) {
                System.out.print(x + ", ");
            }
            System.out.println("]");
        }
        p.printAll();
    }


    public void solve(Problem p)
    {
        search(p);
    }

    public void search(Problem p)
    {
        this.p = p;
        status = Status.UNKNOWN;
        p.consistent = true;

        int i = 1;

        while (status == Status.UNKNOWN) {
            if (p.consistent)
                i = label(i);
            else
                i = unlabel(i);

            if (i > p.numVariables) {
                ++numSolutions;    // Now we've found one iteration, we try to find another until there is none
                p.solutions.add(Arrays.copyOfRange(p.variables, 1, p.variables.length));
                i -= 1;
                p.consistent = false;
            } else if (i == 0)
                status = Status.IMPOSSIBLE;

            ++numIterations;
        }
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
                    p.currentDomain[i-1].remove((Integer)p.variables[i-1]);
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
        int h = i - 1;
        // Rather than store any domain set for the fake variable, we assign the domain as a null pointer and just
        // check for when we try to unlabel the first possible variable
        if (h == 0)
            return h;
        p.currentDomain[i] = new ArrayList<>(Arrays.asList(p.domain.clone()));
        p.currentDomain[h].remove((Integer)p.variables[h]);
        p.consistent = !p.currentDomain[h].isEmpty();
        return h;
    }

    /**
     * Check tells if the constraint between variable i and variable h holds given their current values.
     * @param i The current variable's index
     * @param h The preceding variable's index
     * @return  true if the constraint between variable i and variable h holds otherwise false
     */
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
        if (this.status == Status.IMPOSSIBLE){
            s.append("]");
            return s.toString();
        }
        for (int i = 1; i < this.p.variables.length-1; i++)
            s.append(this.p.variables[i] + ", ");
        s.append(this.p.variables[this.p.variables.length-1]+"]");
        return s.toString();
    }

}
