package telran.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.LinkedList.Node;

import java.util.Arrays;
import java.util.Random;

public abstract class SortedSetTest extends SetTest{
    SortedSet<Integer> sortedSet;

    @Override
    void setUp() {
        super.setUp();
        sortedSet = (SortedSet<Integer>) collection;
    }

    @Test
    void floorTest() {
        assertEquals(10, sortedSet.floor(10));
        assertNull(sortedSet.floor(-11));
        assertEquals(10, sortedSet.floor(11));
        assertEquals(100, sortedSet.floor(101));
        assertEquals(1, sortedSet.floor(2));
        assertEquals(3, sortedSet.floor(4));
    }

    @Test
    void ceilingTest() {
        assertEquals(10, sortedSet.ceiling(10));
        assertNull(sortedSet.ceiling(101));
        assertEquals(17, sortedSet.ceiling(11));
        assertEquals(-10, sortedSet.ceiling(-11));
        assertEquals(3, sortedSet.ceiling(2));
        assertEquals(8, sortedSet.ceiling(4));
    }

    @Test
    void firstTest() {
        assertEquals(-10, sortedSet.first());
    }

    @Test
    void lastTest() {
        assertEquals(100, sortedSet.last());
    }

    @Test
    void subSetTest() {
        Integer[] expected = {10, 17};
        Integer[] actual = sortedSet.subSet(10, 20).stream().toArray(Integer[]::new);
        assertArrayEquals(expected, actual);
    }

    @Override
    protected void fillBigCollection() {
        Integer[] array = getBigArrayCW();
        Arrays.stream(array).forEach(collection::add);
    }

    protected Integer[] getBigArrayCW() {
        return new Random().ints().distinct().limit(N_ELEMENTS).boxed().toArray(Integer[]::new);
    }

    protected Integer[] getBigArrayHW() {
        Integer[] array = new Random().ints().distinct().limit(N_ELEMENTS).boxed().toArray(Integer[]::new);
        Arrays.sort(array);
        Integer[] balanceArray = new Integer[array.length];
        sortedArrayForbalanceTree(array, balanceArray, 0, array.length - 1, 0);
        return balanceArray;
    }

    private void sortedArrayForbalanceTree(Integer[] array, Integer[] balanceArray, int left, int right, int currentIndex) {
        if (left <= right) {
            int middle = (left + right) / 2;
            balanceArray[currentIndex] = array[middle];
            sortedArrayForbalanceTree(array, balanceArray, left, middle - 1, 2 * currentIndex + 1);
            sortedArrayForbalanceTree(array, balanceArray, middle + 1, right, 2 * currentIndex + 2);  
        } 
    }

    @Override
    protected void runTest(Integer[] expected) {
        Integer[] expectedSorted = Arrays.copyOf(expected, expected.length);
        Arrays.sort(expectedSorted);
        Integer[] actual = collection.stream().toArray(Integer[]::new);
        assertArrayEquals(expectedSorted, actual);
        assertEquals(expected.length, collection.size());
    }
}
