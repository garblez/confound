package uk.ac.gla.confound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class NQueens {

    enum Status {
        UNKNOWN, IMPOSSIBLE, SOLUTION
    }

    static final int NIL = -1;

    Status status;
    boolean consistent;
    int numQueens;

    int[] variables; // For each variables[i] := queen row |-> col, of size numVar+1 as variables[0] is always null
    Integer[] domain; // Assume all integer variables have the same domain
    ArrayList<Integer>[] currentDomain; // current potential domain values for each variable
    BitSet[] constraints;  // Constraint bitset for each variable


    public NQueens(int numQueens) {
        this.numQueens = numQueens;
        variables = new int[numQueens + 1];
        variables[0] = NIL;

        // Initialise the domain
        domain = new Integer[numQueens];
        for (int i = 0; i < numQueens; i++)
            domain[i] = i;

        // Set up variables' current domains
        currentDomain = new ArrayList[numQueens+1];
        currentDomain[0] = null;                    // TODO: Double-check ramifications (potential NullPointerExceptions)
        for (int i = 1; i <= numQueens; i++) {
            currentDomain[i] = new ArrayList<Integer>();
            for (Integer value: domain.clone())
                currentDomain[i].add(value);
        }

        // Initialise a has-constraint table
        constraints = new BitSet[numQueens];
        for (int i = 0; i < numQueens; i++)
            constraints[i] = new BitSet(numQueens);
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
        for (int i = 0; i < numQueens; i++) {
            for (int j = 0; j < numQueens; j++) {
                constraints[i].set(j, i != j);
            }
        }
    }



    void search()
    {
        status = Status.UNKNOWN;
        consistent = true;

        int i = 1;

        while (status == Status.UNKNOWN) {
            System.out.print("i: "+i + ", variables: [");
            for (Integer x: variables)
                System.out.print(x + ", ");
            System.out.println("]");

            if (consistent)
                i = label(i);
            else
                i = unlabel(i);

            if (i > numQueens)
                status = Status.SOLUTION;
            else if (i == 0)
                status = Status.IMPOSSIBLE;
        }
    }


    int label(int i)

    {
        System.out.println("label");
        consistent = false;

        // Check each value variable[i] *could* be until we have a consistent value or we exhaust all current possibilities
        for (int j = 0; j < currentDomain[i].size() && !consistent; j++) {
            System.out.println("Label outer loop: i: "+i);
            //if (j >= numQueens)
            //    return i;

            //if (currentDomain[i].isEmpty())
            //    break;  // We have an empty currentDomain TODO: MAY REMOVE THIS AS AN ISEMPTY BEFORE THE LOOP MAY WORK
            //else
            //    j++;  // Check the next available choice in the currentDomain
            //
            System.out.println("Next variable[i]: "+ ((ArrayList<Integer>)currentDomain[i]).get(j));
            variables[i] = (Integer)currentDomain[i].get(j);
            consistent = true;
            // Run through all previously chosen variables and check if they are all consistent with the current candidate
            // variable[i]
            for (int h = 1; h < i && consistent; h++) {
                consistent = check(i, h);    // Check that the constraint between variable[i] and variable[h] holds

                // Remove value from candidates on constraint failure
                if (!consistent)
                    //remove(currentDomain[i-1], variables[i]);
                    currentDomain[i-1].remove((Integer)variables[i]);
            }
        }
        if (consistent)
            return i + 1;
        else
            return i;
    }



    // Remove the first (and only instance) of element from array
    void remove(int[] array, int element)
    {
        for (int i = 0; i < array.length; i++)
            if (array[i] == element) {
                array[i] = NIL;
                break; // Quit fast: we assume array is a set of distinct elements
            }
    }


    int unlabel(int i)
    {
        int h = i - 1;

        System.out.println("Unlabel (i: "+i+", h: "+h+")");
        currentDomain[i].addAll(Arrays.asList(domain.clone()));
        currentDomain[h].remove((Integer)variables[h]);
        consistent = !currentDomain[h].isEmpty();
        System.out.println("Consistent: "+consistent);
        return h;
    }

    boolean isEmpty(int[] array)
    {
        for (int i = 0; i < array.length; i++)
            if (array[i] != NIL)
                return false;
        return true;
    }

    boolean check(int i, int h)
    {
        if (constraints[i-1].get(h-1))
            return variables[i] != variables[h] && Math.abs(variables[i] - variables[h]) != Math.abs(i - h);
        return true; // No `inequal` constraint between i and h so the result is consistent for any choice of i
    }


    public static void main(String[] args)
    {
        // With our implementation, queens never share rows - we must check what columns they can have!
        NQueens nQueens = new NQueens(5);
        nQueens.search();

        System.out.print(nQueens.status + ": ");
        for (int i = 1; i < nQueens.variables.length; i++)
            System.out.print(nQueens.variables[i] + " ");
    }
}
