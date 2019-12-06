package uk.ac.gla.confound;


import java.util.*;

public abstract class Problem {
    boolean consistent;
    int numVariables;

    int[] variables; // For each variables[i] := queen row |-> col, of size numVar+1 as variables[0] is always null
    Integer[] domain; // Assume all integer variables have the same domain
    ArrayList<Integer>[] currentDomain; // current potential domain values for each variable
    BitSet[] constraints;  // Constraint bitset for each variable

    List<int[]> solutions;

    public Problem(int numVariables) {
        this.numVariables = numVariables;
        variables = new int[this.numVariables + 1];

        // Initialise the domain
        domain = new Integer[this.numVariables];
        for (int i = 0; i < this.numVariables; i++)
            domain[i] = i;

        // Set up variables' current domains
        currentDomain = new ArrayList[this.numVariables +1];
        currentDomain[0] = null;
        for (int i = 1; i <= this.numVariables; i++) {
            currentDomain[i] = new ArrayList<>();
            for (Integer value: domain.clone())
                currentDomain[i].add(value);
        }

        // Initialise a has-constraint table
        constraints = new BitSet[this.numVariables];
        for (int i = 0; i < this.numVariables; i++)
            constraints[i] = new BitSet(this.numVariables);

        solutions = new ArrayList<>();
    }


    public void print(int x)
    {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < numVariables; i++) {
            for (int j = 0; j < numVariables; j++) {
                if (j==solutions.get(x)[i])
                    s.append("*");
                else
                    s.append("  ");
            }
            s.append("\n");
        }
        for (int i = 0; i < numVariables; i++)
            s.append("==");
        s.append("\n");
        System.out.print(s.toString());
    }

    public boolean check(int i, int j)
    {
        return true;
    }

    public void printAll()
    {
        for (int i = 0; i < solutions.size(); i++)
            print(i);
    }
}
