package telran.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashSetTest extends SetTest {
    @BeforeEach
    @Override
    void setUp() {
        collection = new HashSet<>();
        super.setUp();
    }

    @Override
    protected void runTest(Integer[] expected) {
        Integer[] expectedSorted = Arrays.copyOf(expected, expected.length);
        Arrays.sort(expectedSorted);
        Integer[] actual = collection.stream().toArray(Integer[]::new);
        Arrays.sort(actual);
        assertArrayEquals(expectedSorted, actual);
        assertEquals(expected.length, collection.size());
    }

    @Override
    void iteratorTest() {
        Integer[] actual = new Integer[array.length];
        int index = 0;
        Iterator<Integer> it = collection.iterator();
     
        while(it.hasNext()) {
            actual[index++] = it.next();
        }
        
        Arrays.sort(array);
        Arrays.sort(actual);
        assertArrayEquals(array, actual);
        assertThrowsExactly(NoSuchElementException.class, it::next);
    }
}
