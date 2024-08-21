package telran.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract public class ListTest extends CollectionTest{
    List<Integer> list;

    @BeforeEach
    @Override
    void setUp() {
        super.setUp();
        list = (List<Integer>) collection;
    }

    @Test
    @Override
    void addTest() {
        list.add(5, 85);
        Object[] array1 = list.stream().limit(9).toArray();
        Object[] array2 = {3, -10, 20, 1, 10, 85, 8, 100 , 17};
        assertArrayEquals(array1, array2);
    }

    @Test
    @Override
    void removeTest() {
        assertEquals(Integer.valueOf(20), list.remove(2));
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> list.remove(100));    
    }
    
    @Test
    void getTest() {
        assertEquals(Integer.valueOf(20), list.get(2));
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> list.remove(100));    
    }

    @Test
    void indexOfTest() {
        assertEquals(2, list.indexOf(20));   
        assertEquals(-1, list.indexOf(200)); 
    }

    @Test
    void lastIndexOfTest() {
        assertEquals(6, list.indexOf(100));   
        assertEquals(-1, list.indexOf(200)); 
    }

}