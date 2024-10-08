package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Arrays;
import java.util.function.Predicate;

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
        reallocationIfNeeded();
        array[size++] = obj;
        return true;
    }

    private void reallocationIfNeeded() {
        if(size == array.length) {
            reallocate();
        }
    }

    private void reallocate() {
        array = Arrays.copyOf(array, array.length * 2);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {
        int currentIndex;
        private boolean flNext = false;

        public ArrayIterator() {
            this.currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            flNext = true;
            return (T) array[currentIndex++];
        }

        @Override
        public void remove() {
            if (!flNext) {
                throw new IllegalStateException();
            }
            ArrayList.this.remove(--currentIndex);
            flNext = false;
        }

    }

    @Override
    public void add(int index, T obj) {
        checkIndex(index, true);
        reallocationIfNeeded();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = obj;
        size++;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        T res = (T)array[index];
        size--;
        System.arraycopy(array, index + 1, array, index, size - index);
        array[size] = null;
        return res;
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);
        return (T) array[index];
    }

    @Override
    public int indexOf(T pattern) {
        int index = 0;

        while (index < size && !Objects.equals(array[index], pattern)) {
            index++;
        }

        return index == size ? -1 : index;
    }

    @Override
    public int lastIndexOf(T pattern) {
        int index = size - 1;

        while (index >= 0 && !Objects.equals(array[index], pattern)) {
            index--;
        }

        return index;
    }

    @Override
    public boolean removeIf(Predicate<T> predicate) {
        //algorithm complexity O[N]
        //hint: two indexies and going througth one array
        int  oldSize = size;
        int newSize = 0;
        for (int i = 0; i < oldSize; i++) {
            if (!predicate.test((T)array[i])) {
                if (newSize != i) {
                    array[newSize] = array[i];
                    array[i] = null;
                }
                newSize++;
            } else {
                array[i] = null;
                size--;
            }
        }
        return size < oldSize;
    }

}
