package uk.ac.gla.confound.constraint;

public class IndexPair {
    public int first, second;

    // (Smallest, largest)
    public IndexPair(int fst, int snd)
    {
        if (fst > snd) {
            first = snd;
            second = fst;
        } else {
            first = fst;
            second = snd;
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IndexPair && (
                ((IndexPair) o).first == this.first && ((IndexPair) o).second == this.second
                || ((IndexPair) o).second == this.first && ((IndexPair) o).first == this.second
                );
    }


    @Override
    public int hashCode() {
        return Integer.hashCode(first) + Integer.hashCode(second);
    }

    @Override
    public String toString() {
        return "("+first+", "+second+")";
    }
}
