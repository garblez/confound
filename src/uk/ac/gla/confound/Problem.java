package uk.ac.gla.confound;

import com.sun.org.apache.xpath.internal.operations.String;

import java.util.*;

public abstract class Problem {
    Solver solver;

    Status status;
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
        /*
            Constraint?
                v0  v1  v2  v3  ...
            v0  0   1   1   1
            v1  1   0   1   1
            v2  1   1   0   1
            v3  1   1   1   0
            .
            .
         */
        for (int i = 0; i < this.numVariables; i++) {
            for (int j = 0; j < this.numVariables; j++) {
                constraints[i].set(j, i != j);  // Let there be a constraint between all different variables
            }
        }
        solutions = new ArrayList<int[]>();
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

    public void printAll()
    {
        for (int i = 0; i < solutions.size(); i++)
            print(i);
    }
}
