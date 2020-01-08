package uk.ac.gla.confound.marktwo;


import java.util.Arrays;

public abstract class Solver implements SolverMethods {
    public Problem p;
    public Status status;

    public int numIterations;
    public int numSolutions;
    public int backtracks;
    public double duration;

    public Solver(Problem p) {
        numIterations = 0;
        numSolutions = 0;
        backtracks = 0;
        this.p = p;
    }

    public void solve()
    {
        duration = System.currentTimeMillis();
        status = Status.UNKNOWN;
        p.consistent = true;

        int i = 1;

        while (status == Status.UNKNOWN) {
            if (p.consistent) {
                i = label(i);
            } else {
                i = unlabel(i);
                ++backtracks;
            }

            if (i > p.numVariables) {
                ++numSolutions;    // Now we've found one solution, we try to find another until there are none left
                p.solutions.add(Arrays.copyOfRange(p.variables, 1, p.variables.length));
                i -= 1;
                p.consistent = false;
            } else if (i == 0)
                status = Status.IMPOSSIBLE;


            ++numIterations;
        }
        duration = System.currentTimeMillis() - duration;
    }





    public void report(Problem p)
    {
        System.out.println("Status report");
        System.out.println("#Iterations: "+numIterations);
        System.out.println("Duration: " + duration + "(ms)");
        System.out.println("Backtracks: " + backtracks);
        System.out.println("#Solutions: "+numSolutions);
        System.out.println("Solutions are as follows\n=========================");
        for (Variable[] solution: p.solutions) {
            System.out.print("[");
            for (Variable var : solution) {
                System.out.print(var.value + ", ");
            }
            System.out.println("]");
        }

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
