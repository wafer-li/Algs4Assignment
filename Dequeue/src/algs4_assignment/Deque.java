package algs4_assignment;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is the Deque class.
 * According to the performance requirement,
 * Deque need to guarantee the constant worst-case time,
 * so use the dual linked-list implementation
 *
 * @author Wafer
 * @version 1.0.0
 * @since 2016/2/6 5:39
 */
public class Deque<Item> implements Iterable<Item> {

    private class Node {
        public Item item;
        public Node next;
        public Node prev;

        public Node(Item item) {
            this.item = item;
            next = null;
            prev = null;
        }
    }


    private Node head;
    private Node last;

    private int size;


    private void checkAddNull(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }


    private void checkRemoveFromEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }


    public Deque() {
        head = null;
        last = null;
        size = 0;
    }


    public boolean isEmpty() {
        return size == 0;
    }


    public int size() {
        return this.size;
    }


    public void addFirst(Item item) throws NullPointerException {

        checkAddNull(item);

        Node node = new Node(item);

        if (head == null) {
            head = node;
            last = head;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }

        size++;
    }


    public void addLast(Item item) throws NullPointerException {

        checkAddNull(item);

        Node node = new Node(item);

        if (last == null) {
            last = node;
            head = last;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }

        size++;
    }


    public Item removeFirst() throws NoSuchElementException {

        checkRemoveFromEmpty();

        Item item = head.item;
        head = head.next;
        size--;
        return item;
    }


    public Item removeLast() throws NoSuchElementException {

        checkRemoveFromEmpty();

        Item item = last.item;
        last = last.prev;
        last.next = null;
        size--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }


    private class DequeueIterator implements Iterator<Item> {

        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    public static void main(String[] args) {

        // Normal test
        Deque<Integer> deque = new Deque<>();
        StdOut.println("Is deque empty? " + deque.isEmpty());
        StdOut.println("The size of the deque is " + deque.size());

        StdOut.println();

        deque.addFirst(1);
        StdOut.println("Add 1 at first.");
        StdOut.println("Is deque empty? " + deque.isEmpty());
        StdOut.println("The size of the deque is " + deque.size());

        StdOut.println();

        deque.addLast(2);
        StdOut.println("Add 2 at the last");
        StdOut.println("Is deque empty? " + deque.isEmpty());
        StdOut.println("The size of the deque is " + deque.size());

        StdOut.println();

        int n = deque.removeFirst();
        StdOut.println("Remove from first");
        StdOut.println("The item being removed is " + n);
        StdOut.println("Is deque empty? " + deque.isEmpty());
        StdOut.println("The size of the deque is " + deque.size());

        StdOut.println();

        deque.addFirst(1);
        n = deque.removeLast();
        StdOut.println("The item being removed is " + n);
        StdOut.println("Is deque empty? " + deque.isEmpty());
        StdOut.println("The size of the deque is " + deque.size());

        StdOut.println();

        // Corner case test
        while (!deque.isEmpty()) {
            deque.removeFirst();
        }

        try {
            deque.removeFirst();
        }
        catch (NoSuchElementException e) {
            StdOut.println("NoSuchElementException: Cannot remove from first!");
            StdOut.println();
        }

        try {
            deque.removeLast();
        }
        catch (NoSuchElementException e) {
            StdOut.println("NoSuchElementException: Cannot remove from last!");
            StdOut.println();
        }

        try {
            deque.addFirst(null);
        }
        catch (NullPointerException e) {
            StdOut.println("NullPointerException: Cannot add null object at First!");
            StdOut.println();
        }

        try {
            deque.addLast(null);
        }
        catch (NullPointerException e) {
            StdOut.println("NullPointerException: Cannot add null object at Last!");
            StdOut.println();
        }

        Iterator<Integer> iterator = deque.iterator();

        try {
            iterator.remove();
        }
        catch (UnsupportedOperationException e) {
            StdOut.println("UnsupportedOperationException: We do not support remove() in the iterator!");
            StdOut.println();
        }

        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);

        iterator = deque.iterator();

        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }

        try {
            iterator.next();
        }
        catch (NoSuchElementException e) {
            StdOut.println("NoSuchElementException: End of the iteration!");
            StdOut.println();
        }
    }
}
