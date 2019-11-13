package uk.ac.gla.confound;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abstract base class Solver defines common variables and methods between all extending solvers such as
 * Problem p, the problem class being solved
 * Status status, the current status of the search undertaken by the solver
 * int numIterations, the number of search iterations undertaken
 * int numSolutions, the number of consistent solutions found
 * ArrayList previousValues, a list of the previous values found
 */
public abstract class Solver implements SolverMethods {
    Problem p;
    ArrayList<Integer> previousValues = new ArrayList<>();
    Status status;

    int numIterations;
    int numSolutions;

    public Solver(Problem p) {
        numSolutions = 0;
        numSolutions = 0;
        this.p = p;
    }

    public void solve()
    {
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
     * Check tells if the constraint between variable i and variable h holds given their current values.
     * Right now, this is just one condition for all constraints: later, we may be able to map variable sets to
     * constraints and check those constraint's individual conditions.
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
