package uk.ac.gla.confound.solver;


public class Statistics {
    private int numVariables;
    private int numIterations;
    private int numSolutions;
    private int backtracks;
    private double duration;
    private int[] visits;
    private Status status;


    public Statistics(int n) {
        numVariables = n;
        numIterations = 0;
        numSolutions = 0;
        backtracks = 0;
        duration = System.currentTimeMillis();
        visits = new int[n+1];
        status = Status.UNKNOWN;
    }

    public void incBacktracks() {
        ++backtracks;
    }

    public void incSolutions() {
        ++numSolutions;
    }

    public void incNodesVisited() {
        ++numIterations;
    }

    public void markVisited(int ix) {
        ++visits[ix];
    }

    public int getNumVisits(int ix) {
        return visits[ix];
    }

    public int getMostVisited() {
        int max = 0;
        for (int i = 1; i < visits.length; i++) {
            if (visits[i] > visits[max]) {
                max = i;
            }
        }
        return max;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int[] getVisits() {
        return visits;
    }

    public void setCurrentTime(long duration) {
        this.duration = duration;
    }

    public void findDuration() {
        duration = System.currentTimeMillis() - duration;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Status:"+this.status+"\n");
        sb.append("Nodes visited: "+numIterations+"\n");
        sb.append("Backtracks: "+backtracks+"\n");
        sb.append("Duration: "+0.001*duration+"s\n");
        sb.append("Solutions: "+numSolutions+"\n");
        sb.append("Node visits: \n");
        for (int i = 0; i < visits.length; i++)
            sb.append(i+" "+visits[i]+"\n");
        return sb.toString();
    }
}
