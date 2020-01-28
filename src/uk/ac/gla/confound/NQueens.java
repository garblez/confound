package uk.ac.gla.confound;


import uk.ac.gla.confound.solver.*;


public class NQueens extends Problem {
    public NQueens(int numVars) {
        super(numVars);

        for (int i = 0; i < this.numVariables; i++) {
            for (int j = 0; j < this.numVariables; j++) {
                constraints[i].set(j, i != j);
            }
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
        if (constraints[i-1].get(h-1))
            return !variables[i].equals(variables[h]) && Math.abs(variables[i].value - variables[h].value) != Math.abs(i - h);
        return true;
    }



    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("USAGE: NQueens [Solver] [Num Queens] ");
            System.out.println("BacktrackSolver, ForwardCheckSolver, BackjumpSolver, ConflictBackjumpSolver, DynamicBacktrackSolver");
        } else {
            Problem nQueens = new NQueens(Integer.parseInt(args[1]));
            Solver s;
            switch (args[0]) {
                case "ForwardCheckSolver":
                    s = new ForwardCheckSolver(nQueens);
                    break;
                case "BackjumpSolver":
                    s = new BackjumpSolver(nQueens);
                    break;
                case "ConflictBackjumpSolver":
                    s = new ConflictBackjumpSolver(nQueens);
                    break;
                /*case "DynamicBacktrackSolver":
                    s = new DynamicBacktrackSolver();
                    break;

                 */
                case "BacktrackSolver":
                    // FALL-THROUGH
                default:
                    s = new BacktrackSolver(nQueens);

            }
            s.solve();
            s.report(nQueens);
        }
    }
}
