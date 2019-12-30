package uk.ac.gla.confound;

import java.util.ArrayList;
import java.util.Arrays;

public class Domain {
    ArrayList<Integer> values;

    public Domain(int lb, int ub) {
        values = new ArrayList<Integer>();
        for (int x = lb; x <= ub; x++)
            values.add(x);
    }

    public Domain(Integer... values) {
        this.values = new ArrayList<Integer>();
        this.values.addAll(Arrays.asList(values));
    }


    @Override
    public boolean isEmpty() {
        
    }

}
