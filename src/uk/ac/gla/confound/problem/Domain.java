package uk.ac.gla.confound.problem;

import java.util.ArrayList;
import java.util.Set;

public class Domain {
    private int[] values;

    public Domain(int numValues) {
        this.values = new int[numValues];
        for (int i = 0; i < numValues; i++)
            this.values[i] = i;
    }

    public Domain(int lwb, int upb) {
        this.values = new int[upb-lwb+1];
        for (int i = lwb; i <= upb; i++)
            this.values[i-lwb] = i;
    }

    public Domain(Set<Integer> values) {
        this.values = new int[values.size()];
        for (int i = 0; i < this.values.length; i++)
            this.values[i] = (int)values.toArray()[i];
    }

    public int[] values() {
        return values;
    }

    public ArrayList<Integer> copy() {
        ArrayList<Integer> copy = new ArrayList<>();
        int[] clone = values.clone();
        for (int i = 0; i < values.length; i++)
            copy.add(clone[i]);

        return copy;
    }
}
