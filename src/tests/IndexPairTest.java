package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;



import uk.ac.gla.confound.constraint.AlwaysTrueConstraint;
import uk.ac.gla.confound.constraint.Constraint;
import uk.ac.gla.confound.constraint.IndexPair;

import java.util.HashMap;
import java.util.Map;


public class IndexPairTest {

    IndexPair a;
    IndexPair b;
    IndexPair c;

    Map<IndexPair, Constraint> constraints;

    @Before
    public void setUp() throws Exception {
        a = new IndexPair(1,2);
        b = new IndexPair(1, 3);
        c = new IndexPair(2, 1);
        constraints = new HashMap<>();

        constraints.put(a, new AlwaysTrueConstraint());
        constraints.put(b, new AlwaysTrueConstraint());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testEquals() {
        assertNotEquals(a, b);
        assertEquals(a, c);
        assertEquals(a, new IndexPair(1, 2));
    }

    @Test
    public void testReverseEquals() {
        assertEquals(new IndexPair(0,1), new IndexPair(1, 0));
    }


    @Test
    public void testHashCode() {
        assertNotEquals(a.hashCode(),b.hashCode());
        assertEquals(a.hashCode(), c.hashCode());
    }

    @Test
    public void testTable() {
        assertTrue(constraints.containsKey(new IndexPair(1, 2)));
        assertTrue(constraints.containsKey(new IndexPair(1, 3)));
        assertTrue("Constraints contains c",constraints.containsKey(b));
        assertTrue("Constraints contains key (2,1)", constraints.containsKey(new IndexPair(1,2)));
    }
}