package uk.ac.gla.confound.problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class Domain {
    private int[] values;

    // Singleton domain (a domain of only one value)
    public Domain(int value) {
        this.values = new int[1];
        values[0] = value;
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
        for (int i = 0; i < values.length; i++)
            copy.add(values[i]);
        return copy;
    }

    public int size() {
        return values.length;
    }

}
