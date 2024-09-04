package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Arrays;

@SuppressWarnings("unchecked")
public class HashSet<T> implements Set<T> {
    private static final int DEFAULT_HASH_TABLE_LENGTH = 16;
    private static final float DEFAULT_FACTOR = 0.75f;
    List<T>[] hashTable;
    float factor;
    int size;

    private class HashSetIterator implements Iterator<T> {
        Iterator<T> currentIterator;
        Iterator<T> prevIterator;
        int indexIterator = 0;
        int index = 0;
        List<T> list;
        boolean flNext = false;
        int prevIndex = 0;

        public HashSetIterator() {
            list = hashTable[index++];
            if (list != null) {
                currentIterator = list.iterator();
            }
        }

        @Override
        public boolean hasNext() {
            return indexIterator < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T res = null;
            flNext = true;

            while (res == null && index < hashTable.length) {
                if (list == null) {
                    list = hashTable[index];
                    if (list != null) {
                        prevIndex = index;
                        currentIterator = list.iterator();    
                    } 
                    index++;
                }
        
                if (list != null && currentIterator.hasNext()) {
                    res = currentIterator.next(); 
                    indexIterator++;   
                } else {
                    list = null;
                }  
            }

            return res;
        }

        @Override
        public void remove() {
            if (!flNext) {
                throw new IllegalStateException();
            }

            currentIterator.remove();

            if (!currentIterator.hasNext()) {
                hashTable[prevIndex] = null;
            }

            indexIterator--;
            size--;
            flNext = false;
        }

    }

    public HashSet(int hashTableLenght, float factor) {
        hashTable = new List[hashTableLenght];
        this.factor = factor;
    }

    public HashSet() {
        this(DEFAULT_HASH_TABLE_LENGTH, DEFAULT_FACTOR);
    }

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            res = true;
            if (size >= hashTable.length * factor) {
                hashTableRealocation();
            }
            addObjInHashTable(obj, hashTable);
            size++;
        }
        return res;
    }

    private void addObjInHashTable(T obj, List<T>[] table) {
        int index = getIndex(obj, table.length);
        List<T> list = table[index];
        if (list == null) {
            list = new ArrayList<>(3);
            table[index] = list;
        }
        list.add(obj);
    }

    private int getIndex(T obj, int length) {
        int hashCode = obj.hashCode();
        return Math.abs(hashCode % length);
    }

    private void hashTableRealocation() {
        List<T>[] tempTable = new List[hashTable.length * 2];
        for (List<T> list : hashTable) {
            if (list != null) {
                list.forEach(obj -> addObjInHashTable(obj, tempTable));
                list.clear(); // ??? for testing. May be remove
            }
        }
        hashTable = tempTable;
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;

        int index = getIndex(pattern, hashTable.length);
        List<T> list = hashTable[index];

        if (list != null && list.remove(pattern)) {
            res = true;
            size--;
            if (list.size() == 0) {
                hashTable[index] = null;
            }
        }

        return res;
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
        List<T> list = getInnerList(pattern);
        return list != null && list.contains(pattern);
    }

    @Override
    public Iterator<T> iterator() {
        return new HashSetIterator();
    }

    @Override
    public T get(Object pattern) {
        T res = null;
        T tpattern = (T) pattern;
		if (contains(tpattern)) {
			int index = getIndex(tpattern, hashTable.length);
			List<T> list = hashTable[index];
			int indexInList = list.indexOf(tpattern);
			res = list.get(indexInList);

		}
		return res;
    }

    private List<T> getInnerList(T pattern) {
        int index = getIndex(pattern, hashTable.length);
        return hashTable[index];
    }

}
