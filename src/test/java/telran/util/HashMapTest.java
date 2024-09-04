package telran.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;

public class HashMapTest extends AbstractMapTest {

    @Override
    <T> void runTest(T[] expected, T[] actual) {
        T[] expectedSorted = Arrays.copyOf(expected, expected.length);
        Arrays.sort(expectedSorted);
        T[] actualSorted = Arrays.copyOf(actual, actual.length);
        Arrays.sort(actualSorted);
        assertArrayEquals(expectedSorted, actualSorted);
        assertEquals(expectedSorted.length, actualSorted.length);
    }

    @Override
    @BeforeEach
    void setUp() {
        map = new HashMap<>();
        super.setUp();
    }

}
