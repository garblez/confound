package uk.ac.gla.confound;

import java.util.BitSet;

public class NQueens {

    enum Status {
        UNKNOWN, IMPOSSIBLE, SOLUTION
    }

    static final int NIL = -1;

    Status status;
    boolean consistent;
    int numQueens;

    int[] variables; // For each variables[i] := queen row |-> col, of size numVar+1 as variables[0] is always null
    int[] domain; // Assume all integer variables have the same domain
    int[][] currentDomain; // domain for each variable, if an element doesn't exist then it's null
    BitSet[] constraints;  // Constraint bitset for each variable


    public NQueens(int numQueens) {
        this.numQueens = numQueens;
        variables = new int[numQueens + 1];
        variables[0] = NIL;

        // Initialise the domain
        domain = new int[numQueens];
        for (int i = 0; i < numQueens; i++)
            domain[i] = i;

        // Set up variables' current domains
        currentDomain = new int[numQueens][];
        for (int i = 0; i < numQueens; i++)
            currentDomain[i] = domain.clone();

        // Initialise a has-constraint table
        constraints = new BitSet[numQueens];
        for (int i = 0; i < numQueens; i++)
            constraints[i] = new BitSet(numQueens);
        /*
            Constraint?
                v0  v1  v2  v3
            v0  0   1   1   1
            v1  1   0   1   1
            v2  1   1   0   1
            v3  1   1   1   0
         */
        constraints[0].set(0, false);
        constraints[0].set(1, true);
        constraints[1].set(0, true);
        constraints[1].set(1, false);
    }

    void printStatus(){
        System.out.println("Status: "+ status + " Consistent: " + consistent);
        System.out.print("Variables: ");
        for (int i = 0; i < variables.length; i++)
            System.out.print(variables[i]);
        System.out.println();
    }

    void search()
    {
        status = Status.UNKNOWN;
        consistent = true;

        int i = 1;


        while (status == Status.UNKNOWN) {
            printStatus();
            System.out.println("--------------------\nIndex: " + i);

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
        System.out.println("\tLabel() at index: " + i);
        consistent = false;
        int[] current = currentDomain[i-1]; // Account for fake variable at i == 0
        // Check each value variable[i] *could* be until we have a consistent value or we exhaust all current possibilities
        for (int j = 0; !consistent; j++) {
            System.out.println("i: "+i+" j: "+ j + " size: "+currentDomain.length);
            if (i > numQueens || j > current.length)
                return i;

            if (j >= current.length || current[j] != NIL)
                break;  // We have an empty currentDomain TODO: MAY REMOVE THIS AS AN ISEMPTY BEFORE THE LOOP MAY WORK
            else
                j++;  // Check the next available choice in the currentDomain

            variables[i] = current[j];
            consistent = true;
            System.out.print("variables: ");
            printArray(variables);
            // Run through all previously chosen variables and check if they are all consistent with the current candidate
            // variable[i]
            for (int h = 1; h < i && consistent; h++) {
                System.out.println("\t\tChecking i:"+i+" against h:"+h);
                consistent = check(i, h);    // Check that the constraint between variable[i] and variable[h] holds

                // Remove value from candidates on constraint failure
                if (!consistent)
                    remove(currentDomain[i-1], variables[i]);
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
        System.out.print("Remove ");
        printArray(array);

        for (int i = 0; i < array.length; i++)
            if (array[i] == element) {
                array[i] = NIL;
                break; // Quit fast: we assume array is a set of elements
            }

        printArray(array);
    }


    int unlabel(int i)
    {
        System.out.println("\tUnlabel() at index: "+i);
        int h = i - 1;
        currentDomain[i-1] = domain.clone();
        remove(currentDomain[h], variables[h]);
        consistent = isEmpty(currentDomain[h]);
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

    public static void printArray(int[] array){
        for (int x: array)
            System.out.print(x + " ");
        System.out.println();
    }

    public static void main(String[] args)
    {
        /*
        For 2 queens:
            _,_,_,_
            _,_,_,q
            _,q,_,_
            _,_,_,_
        queens must not share rows nor columns.
        With our implementation, queens never share rows - we must check what columns they can have!
         */
        NQueens nQueens = new NQueens(2);

        nQueens.search();
        System.out.print(nQueens.status + ": ");
        for (int i = 1; i < nQueens.variables.length; i++)
            System.out.print(nQueens.variables[i] + " ");
    }
}