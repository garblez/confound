package uk.ac.gla.confound;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    enum Status {
        UNKNOWN,
        IMPOSSIBLE,
        SOLUTION
    }

    enum Condition {
        EQUAL, INEQUAL
    }


    class Constraint extends HashMap {
        int variable0;
        int variable1;
        Condition cond;
    }

    boolean check(int var0, int var1) {

    }


    Status status;
    boolean consistent;

    Integer[] variable;
    ArrayList<Integer>[] domain;
    ArrayList<Integer>[] currentDomain;
    HashMap<>




    void bcssp(int n) {
        status = Status.UNKNOWN;
        consistent = true;
        int i = 1;

        while (status == Status.UNKNOWN) {
            if (consistent)
                i = label(i);
            else
                i = unlabel(i);

            if (i > n)
                status = Status.SOLUTION;
            else if (i == 0)
                status = Status.IMPOSSIBLE;
        }
    }

    int label(int i) {
        consistent = false;

        // Check each value variable[i] *could* be until we have a consistent value or we exhaust all current possibilities
        for (int j = 0; !consistent; j++) {
            variable[i] = currentDomain[i].get(j);
            consistent = true;

            // Run through all previously chosen variables and check if they are all consistent with the current candidate
            // variable[i]
            for (int h = 1; h < i && consistent; h++) {
                consistent = check(i, h);    // Check that the constraint between variable[i] and variable[h] holds

                // Remove value from candidates on constraint failure
                if (!consistent)
                    currentDomain[i].remove(variable[i]);
            }
        }
        if (consistent)
            return i + 1;
        else
            return i;
    }


    int unlabel(int i) {
        int h = i - 1;
        currentDomain[i] = (ArrayList<Integer>)domain[i].clone();
        currentDomain[h].remove(variable[h]);
        consistent = currentDomain[h].isEmpty();
        return h;
    }


    public static void main(String[] args) {
	// write your code here
    }
}
