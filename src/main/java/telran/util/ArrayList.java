package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Arrays;

public class ArrayList<T> implements List<T> {

    private static final int DEAFAULT_CAPACITY = 16;
    private Object[] array;
    private int size;

    public ArrayList(int capacity) {
        array = new Object[capacity];
    }

    public ArrayList() {
        this(DEAFAULT_CAPACITY);
    }

    @Override
    public boolean add(T obj) {
        if (size == array.length) {
            reallocate();
        }
        array[size++] = obj;
        return true;
    }

    private void reallocate() {
        array = Arrays.copyOf(array, array.length * 2);
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;
        for (int i = 0; i < size; i++) {
            if (array[i] != null && array[i].equals(pattern)) {
                moveArrayLeft(i);
                res = true;
            }
        }
        return res;
    }

    private void moveArrayLeft(int index) {
        for (int i = index; i < size; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
        size--;    
    }

    private void moveArrayRight(int index, T obj) {
        for (int i = size - 1; i >= index; i--) {
            array[i + 1] = array[i];
        }
        array[index] = obj;
        size++;    
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        boolean res = true;
        int index = 0;
        
        while (index < array.length && res) {
            if (array[index] != null) {
                res = false;
            }
            index++;
        }

        return res;
    }

    @Override
    public boolean contains(T pattern) {
        return indexOf(pattern) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {
        int currentIndex;

        public ArrayIterator() {
            this.currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < array.length;
        }

        @Override
        public T next() {
            if (! hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) array[currentIndex++];
        }
    }

    @Override
    public void add(int index, T obj) {
        if (size == array.length) {
            reallocate();
        }
        moveArrayRight(index, obj);
    }

    @Override
    public T remove(int index) {
        T res = (T) array[index];
        moveArrayLeft(index);
        return res;
    }

    @Override
    public T get(int index) {
        return (T) array[index];
    }

    @Override
    public int indexOf(T pattern) {
        int res = -1;
        int index = 0;
        
        while (index < size && res == -1) {
            if (array[index] != null && array[index].equals(pattern)) {
                res = index;
            }
            index++;
        }

        return res;
    }

    @Override
    public int lastIndexOf(T pattern) {
        int res = -1;
        int index = size - 1;
        
        while (index > 0 && res == -1) {
            if (array[index] != null && array[index].equals(pattern)) {
                res = index;
            }
            index--;
        }

        return res;
    }

}
