package uk.ac.gla.confound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Domain {
    private int[] values;

    public Domain(int numValues) {
        this.values = new int[numValues];
        for (int i = 0; i < numValues; i++)
            this.values[i] = i;
        System.out.println("numvals: "+numValues);

    }

    public Domain(int[] values) {
        this.values = values;
    }

    public int[] values() {
        return values;
    }

    // Interval of [start, end)
    public Domain(int start, int end) {
        values = new int[end-start+1];
        for (int i = start; i < end; i++)
            values[i-start] = i;
    }

    public ArrayList<Integer> copy() {
        ArrayList<Integer> copy = new ArrayList<>();
        int[] clone = values.clone();
        for (int i = 0; i < values.length; i++)
            copy.add(clone[i]);

        return copy;
    }
}
