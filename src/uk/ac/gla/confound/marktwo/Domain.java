package uk.ac.gla.confound.marktwo;

import java.util.ArrayList;
import java.util.Arrays;

public class Domain {
    public ArrayList<Integer> values;

    // Domain given as [lb, ub) integer set
    public Domain(int lb, int ub) {
        values = new ArrayList<Integer>();
        for (int x = lb; x < ub; x++)
            values.add(x);
    }

    public Domain(Integer... values) {
        this.values = new ArrayList<Integer>();
        this.values.addAll(Arrays.asList(values));
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public int size() {
        return values.size();
    }

    public Domain clone() {
        return new Domain(this.values.get(0), this.values.get(this.values.size()-1)+1);
    }

    public Integer get(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= values.size()) throw new IndexOutOfBoundsException();
        return values.get(i);
    }

    public void remove(Integer value) {
        values.remove(value);
    }
}
