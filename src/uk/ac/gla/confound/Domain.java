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
