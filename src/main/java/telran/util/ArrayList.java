package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
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

    private void checkIndex(int index, boolean sizeInclusive) {
        int limit = sizeInclusive ? size : size - 1;
        if (index < 0 || index > limit) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(array[i], pattern)) {
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
        return size == 0;
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
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) array[currentIndex++];
        }
    }

    @Override
    public void add(int index, T obj) {
        checkIndex(index, true);
        if (size == array.length) {
            reallocate();
        }
        moveArrayRight(index, obj);
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        T res = (T) array[index];
        moveArrayLeft(index);
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

}
