package telran.util;

import java.util.Iterator;
import java.util.Objects;
import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T> {
    private static class Node<T> {
        T obj;
        Node<T> next;
        Node<T> prev;

        Node(T obj) {
            this.obj = obj;
        }
    }

    private class LinkedListIterator implements Iterator<T> {
        Node<T> current = head;
        boolean flNext = false;

        @Override
        public boolean hasNext() {
            flNext = true;
            return current != null;
        }

        @Override
        public T next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            Node<T> temp = current;
            current = current.next;
            return temp.obj;
        }
        
        @Override
        public void remove() {
            if (!flNext) {
                throw new IllegalStateException();
            }
            LinkedList.this.remove(size == 1 ? tail.obj : current.prev.obj);    
            flNext = false;
        }
    }

    Node<T> head;
    Node<T> tail;
    int size = 0;

    private Node<T> getNode(int index) {
        return index < size / 2 ? getNodeFromHead(index) : getNodeFromTail(index);
    }

    private Node<T> getNodeFromTail(int index) {
        Node<T> current = tail;

        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }

        return current;
    }

    private Node<T> getNodeFromHead(int index) {
        Node<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current;
    }

    private void addNode(Node<T> node, int index) {
        if (index == 0) {
            addHead(node);
        } else if (index == size) {
            addTail(node);
        } else {
            addMiddle(node, index);
        }
        size++;
    }

    private void addMiddle(Node<T> nodeToInsert, int index) {
        Node<T> nodeBefor = getNode(index);
        Node<T> nodeAfter = nodeBefor.prev;
        nodeToInsert.next = nodeBefor;
        nodeToInsert.prev = nodeAfter;
        nodeBefor.prev = nodeToInsert;
        nodeAfter.next = nodeToInsert;
    }

    private void addTail(Node<T> node) {
        tail.next = node;
        node.prev = tail;
        tail = node;
    }

    private void addHead(Node<T> node) {
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    @Override
    public boolean add(T obj) {
        Node<T> node = new Node<>(obj);
        addNode(node, size);
        return true;
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
        return new LinkedListIterator();
    }

    @Override
    public void add(int index, T obj) {
        checkIndex(index, true);
        Node<T> node = new Node<>(obj);
        addNode(node, index);    
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        Node<T> node = getNode(index);

        if (size == 1) {
            head = null;
            tail = null;   
        } else if (index == 0) {
            removeHead(node);  
        } else if (index == size - 1) {
            removeTail(node);
        } else {
            removeMiddle(node);
        }

        size--;

        return node.obj;
    }

    private void removeHead(Node<T> currentNode) {
        Node<T> nodeNext = currentNode.next;
        nodeNext.prev = null;
        head = nodeNext;        
    }

    private void removeTail(Node<T> currentNode) {
        Node<T> nodePrev = currentNode.prev;
        nodePrev.next = null;
        tail = nodePrev;    
    }

    private void removeMiddle(Node<T> currentNode) {
        Node<T> nodeNext = currentNode.next;
        Node<T> nodePrev = currentNode.prev;
        nodePrev.next = nodeNext;
        nodeNext.prev = nodePrev;    
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);
        return getNode(index).obj;
    }

    @Override
    public int indexOf(T pattern) {
        int index = 0;
        Node<T> current = head;

        while (index < size && !Objects.equals(current.obj, pattern)) {
            current = current.next;
            index++;
        }

        return index == size ? -1 : index;
    }

    @Override
    public int lastIndexOf(T pattern) {
        int index = size - 1;
        Node<T> current = tail;

        while (index >= 0 && !Objects.equals(current.obj, pattern)) {
            current = current.prev;
            index--;
        }

        return index;
    }

}
