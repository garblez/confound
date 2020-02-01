package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.gla.confound.problem.Domain;

import java.util.*;

import static org.junit.Assert.*;

public class DomainTest {

    Domain a;
    Domain b;
    Domain c;
    Set d;

    @Before
    public void setUp() throws Exception {
        a = new Domain(8);
        b = new Domain(0, 8);
        d = new HashSet();
        d.addAll(Collections.singleton(new int[]{0, 1, 2, 3, 4}));
        c = new Domain(d);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void values() {
        assertEquals(a.values(), new int[]{0,1,2,3,4,5,6,7,8});
        assertEquals(b.values(), new int[]{0,1,2,3,4,5,6,7,8});
    }

    @Test
    public void copy() {
    }

}