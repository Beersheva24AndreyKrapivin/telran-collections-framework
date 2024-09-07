package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class TreeSet<T> implements SortedSet<T> {
    private static class Node<T> {
        T obj;
        Node<T> parent;
        Node<T> left;
        Node<T> right;

        Node(T obj) {
            this.obj = obj;
        }
    }

    private class TreeSetIterator implements Iterator<T> {
        Node<T> current = null;
        Node<T> prev = null;
        int currentIndex;
        private boolean flNext = false;

        public TreeSetIterator() {
            this.currentIndex = 0;
            if (root != null) {
                current = getLeastFrom(root);
            }
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
            prev = current;

            current = getNextCurrent(current);
            currentIndex++;

            return prev.obj;

        }

        @Override
        public void remove() {
            if (!flNext) {
                throw new IllegalStateException();
            }
            if (prev == null) {
                throw new IllegalStateException();
            }
            removeNode(prev);
            prev = null;
            flNext = false;
            size--;
            currentIndex--;
        }
    }

    private Node<T> root;
    private Comparator<T> comparator;
    private int size;

    private enum SearchType {
        MIN, MAX
    }

    public TreeSet(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public TreeSet() {
        this((Comparator<T>) Comparator.naturalOrder());
    }

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            res = true;
            Node<T> node = new Node<T>(obj);
            if (root == null) {
                addRoot(node);
            } else {
                addAfterParent(node);
            }
            size++;
        }
        return res;
    }

    private void addAfterParent(Node<T> node) {
        Node<T> parent = getParent(node.obj);
        if (comparator.compare(node.obj, parent.obj) > 0) {
            parent.right = node;
        } else {
            parent.left = node;
        }
        node.parent = parent;
    }

    private void addRoot(Node<T> node) {
        root = node;
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;

        Node<T> node = getNode(pattern);

        if (node != null) {
            removeNode(node);
            node = null;
            res = true;
            size--;
        }

        return res;
    }

    private void removeNode(Node<T> node) {
        if (node.left == null && node.right == null) {
            removeNodeWithoutChild(node);
        } else if (isNodeWithOneChild(node)) {
            removeNodeWithOneChild(node);
        } else {
            removeNodeWithTwoChild(node);
        }
    }

    private void removeNodeWithTwoChild(Node<T> node) {
        Node<T> nodeForTransit = getGreatestFrom(node.left);
        node.obj = nodeForTransit.obj;
        removeNode(nodeForTransit);
    }

    private Node<T> getGreatestFrom(Node<T> node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    private Node<T> getLeastFrom(Node<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private Node<T> getNextCurrent(Node<T> current) {
        Node<T> res = null;

        if (current.right != null) {
            res = getLeastFrom(current.right);
        } else if (current.right == null) {
            res = getGreaterParent(current);
        }

        return res;
    }

    private Node<T> getGreaterParent(Node<T> current) {
        Node<T> parent = current.parent;
        while (parent != null && comparator.compare(parent.obj, current.obj) < 0) {
            parent = parent.parent;
        }

        return parent == null ? null : parent;
    }

    private void removeNodeWithOneChild(Node<T> node) {
        Node<T> child = node.left != null ? node.left : node.right;
        Node<T> parent = node.parent;

        if (node != root) {
            if (comparator.compare(parent.obj, child.obj) > 0) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        } else {
            root = child;
        }

        child.parent = parent;
    }

    private void removeNodeWithoutChild(Node<T> node) {
        Node<T> parent = node.parent;
        if (node == root) {
            root = null;
        } else if (parent.left == node) {
            parent.left = null;
        } else {
            parent.right = null;
        }
    }

    private boolean isNodeWithOneChild(Node<T> node) {
        return node.left == null && node.right != null || node.left != null && node.right == null;
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
        return getNode(pattern) != null;
    }

    @Override
    public Iterator<T> iterator() {
        return new TreeSetIterator();
    }

    @Override
    public T get(Object pattern) {
        Node<T> res = getNode((T) pattern);
        return res == null ? null : res.obj;
    }

    private Node<T> getParentOrNode(T pattern) {
        Node<T> current = root;
        Node<T> parent = null;
        int compRes = 0;
        while (current != null && (compRes = comparator.compare(pattern, current.obj)) != 0) {
            parent = current;
            current = compRes > 0 ? current.right : current.left;
        }
        return current == null ? parent : current;
    }

    private Node<T> getNodeOrBoundary(T pattern, SearchType searchType) {
        Node<T> current = root;
        Node<T> find = null;
        int compRes = 0;
        while (current != null && (compRes = comparator.compare(pattern, current.obj)) != 0) {
            if (searchType == SearchType.MIN && compRes > 0
                || searchType == SearchType.MAX && compRes < 0) {
                find = current;
            }
            current = compRes > 0 ? current.right : current.left;
        }
        return current == null ? find : current;
    }

    private Node<T> getNode(T pattern) {
        Node<T> res = getParentOrNode(pattern);
        if (res != null) {
            int compRes = comparator.compare(pattern, res.obj);
            res = compRes == 0 ? res : null;
        }

        return res;
    }

    private Node<T> getParent(T pattern) {
        Node<T> res = getParentOrNode(pattern);
        int compRes = comparator.compare(pattern, res.obj);
        return compRes == 0 ? null : res;
    }

    @Override
    public T first() {
        T res = null;
        if (root != null) {
            res = getLeastFrom(root).obj;
        }
        return res;
    }

    @Override
    public T last() {
        T res = null;
        if (root != null) {
            res = getGreatestFrom(root).obj;
        }
        return res;
    }

    @Override
    public T floor(T key) {
        Node<T> node = getNodeOrBoundary(key, SearchType.MIN);
        return node != null ? node.obj : null;
    }

    @Override
    public T ceiling(T key) {
        Node<T> node = getNodeOrBoundary(key, SearchType.MAX);
        return node != null ? node.obj : null;
    }

    @Override
    public SortedSet<T> subSet(T keyFrom, T keyTo) {
        SortedSet<T> res = new TreeSet<>();
        int compMax = -1;
        
        Iterator<T> iterator = iterator();
        while (iterator.hasNext() && compMax < 0) {
            T current = iterator.next();
            if (comparator.compare(current, keyFrom) >= 0 && (compMax = comparator.compare(current, keyTo)) < 0) {
                res.add(current);    
            }    
        }

        return res;
    }

}
