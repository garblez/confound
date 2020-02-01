package uk.ac.gla.confound.solver;


import uk.ac.gla.confound.NQueens;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.problem.Variable;

import java.util.Scanner;

public class BacktrackSolver extends Solver {

    public BacktrackSolver(Problem p) {
        super(p);
        NAME = "BacktrackSolver";
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
        for (int j = 0; j < p.current[i].size() && !p.consistent; j++) {
            p.variables[i].value = p.current[i].get(j);


            p.consistent = true;
            // Run through all previously chosen variables and check if they are all consistent with the current candidate
            // variable[i]
            for (int h = 1; h < i && p.consistent; h++) {
                // Remove value from candidates on constraint failure
                if (!(p.consistent = p.check(i, h))) {
                    p.current[i-1].remove(Integer.valueOf(p.variables[i-1].value));
                }
            }
        }

        for (Variable var: p.variables)
            System.out.print(var.value +", ");
        System.out.println();

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
        p.current[i] = p.domain.copy();
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
        Solver solver = new BacktrackSolver(nQueens);
        solver.solve();
        solver.report(nQueens);
    }

}
