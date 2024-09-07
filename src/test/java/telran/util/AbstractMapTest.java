package telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public abstract class AbstractMapTest {
    Map<Integer, Integer> map;
    Integer[] array = {3, 10, 20, 1, 30, 8, 100 , 17};

    void setUp() {
        Arrays.stream(array).forEach(n -> map.put(n, n * n));
    }
    abstract <T> void runTest(T[] expected, T[] actual);

    @Test
    void getTest() {
        assertEquals(9, map.get(3));
        assertEquals(100, map.get(10));
        assertEquals(289, map.get(17));
        assertEquals(null, map.get(10000));
    }

    @Test
    void getOrDefaultTest() {
        assertEquals(9, map.getOrDefault(3, 200));
        assertEquals(100, map.getOrDefault(10, 200));
        assertEquals(289, map.getOrDefault(17, 200));
        assertEquals(200, map.getOrDefault(10000, 200));
    }

    @Test
    void putTest() {
        assertEquals(null, map.put(200, 40000));
        assertEquals(10000, map.put(100, 20000));
        
        Integer[] expectedKey = {3, 10, 20, 1, 30, 8, 100 , 17, 200};
        Integer[] expectedValue = {9, 100, 400, 1, 900, 64, 20000 , 289, 40000};
        Integer[] actualValue = map.values().stream().toArray(Integer[]::new);
        Integer[] actualKey = map.keySet().stream().toArray(Integer[]::new);

        runTest(expectedKey, actualKey);
        runTest(expectedValue, actualValue);    
    }

    @Test
    void putIfAbsentTest() {
        assertEquals(null, map.put(200, 40000));
        assertEquals(10000, map.put(100, 20000));
        
        Integer[] expectedKey = {3, 10, 20, 1, 30, 8, 100 , 17, 200};
        Integer[] expectedValue = {9, 100, 400, 1, 900, 64, 20000 , 289, 40000};
        Integer[] actualValue = map.values().stream().toArray(Integer[]::new);
        Integer[] actualKey = map.keySet().stream().toArray(Integer[]::new);

        runTest(expectedKey, actualKey);
        runTest(expectedValue, actualValue);    
    }

    @Test
    void containsKeyTest() {
        assertTrue(map.containsKey(20));  
        assertFalse(map.containsKey(300));    
    }

    @Test
    void containsValueTest() {
        assertTrue(map.containsValue(400));  
        assertFalse(map.containsValue(1551));    
    }

    @Test
    void keySetTest() {
        Set<Integer> keySet = map.keySet();
        Integer[] expectedArray = {3, 10, 20, 1, 30, 8, 100 , 17};
        runTest(expectedArray, keySet.stream().toArray(Integer[]::new));  
    }

    @Test
    void valuesTest() {
        Collection<Integer> values = map.values();
        Integer[] expectedArray = {9, 100, 400, 1, 900, 64, 10000 , 289};
        runTest(expectedArray, values.stream().toArray(Integer[]::new));    
    }

    @Test
    void sizeTest() {
        assertEquals(array.length, map.size());    
    }

    @Test
    void isEmptyTest() {
        assertFalse(map.isEmpty()); 
        map.entrySet().clear();
        assertTrue(map.isEmpty()); 
    }

    
}
