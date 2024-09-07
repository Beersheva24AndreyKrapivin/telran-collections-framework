package telran.util;

import java.util.Iterator;

import telran.util.LinkedList.Node;
import java.util.NoSuchElementException;

public class LinkedHashSet<T> implements Set<T> {
    private LinkedList<T> list = new LinkedList<>();
    HashMap<T, Node<T>> map = new HashMap<>();

    class LinkedHashSetIterator implements Iterator<T> {
        Iterator<T> listIterator;
        T current = null;
        boolean flNext = false;

        public LinkedHashSetIterator() {
            listIterator = list.iterator();
        }

        @Override
        public boolean hasNext() {
            return listIterator.hasNext();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            flNext = true;
            current = listIterator.next();
            return get(current);
        }

        @Override
        public void remove() {
            if (!flNext) {
                throw new IllegalStateException();
            }
            flNext = false;
            LinkedHashSet.this.remove(current);
        }
    }

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            Node<T> node = new Node<>(obj);
            list.addNode(node, list.size());
            map.put(obj, node);
            res = true;
        }
        return res;
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;
        Node<T> removeNode = map.remove(pattern);
        if (removeNode != null) {
            list.removeNode(removeNode);
            res = true;
        }
        return res;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(T pattern) {
        return map.containsKey(pattern);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedHashSetIterator();
    }

    @Override
    public T get(Object pattern) {
        Node<T> res = map.get(pattern);
        return res != null ? res.obj : null;    
    }

}
