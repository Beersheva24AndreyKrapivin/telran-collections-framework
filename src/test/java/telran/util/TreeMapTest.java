package telran.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;

public class TreeMapTest extends AbstractMapTest{

    @Override
    <T> void runTest(T[] expected, T[] actual) {
        T[] expectedSorted = Arrays.copyOf(expected, expected.length);
        Arrays.sort(expectedSorted);
        assertArrayEquals(expectedSorted, actual);
        assertEquals(expectedSorted.length, actual.length);
    }

    @Override
    @BeforeEach
    void setUp() {
        map = new TreeMap<>();
        super.setUp();
    }

}
