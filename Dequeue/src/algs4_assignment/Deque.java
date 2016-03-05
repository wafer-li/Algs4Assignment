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

    public Deque() {
        head = null;
        last = null;
        size = 0;
    }


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


    public boolean isEmpty() {
        return size == 0;
    }


    public int size() {
        return this.size;
    }


    public void addFirst(Item item) {

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


    public void addLast(Item item) {

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


    public Item removeFirst() {

        checkRemoveFromEmpty();

        Item item = head.item;
        head = head.next;

        if (head == null) {
            last = null;
        }
        else {
            head.prev = null;
        }

        size--;
        return item;
    }


    public Item removeLast() {

        checkRemoveFromEmpty();

        Item item = last.item;
        last = last.prev;

        if (last == null) {
            head = null;
        }
        else {
            last.next = null;
        }

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

        // Add first, remove last
        Deque<Integer> deque = new Deque<>();
        StdOut.println("Is deque empty? " + deque.isEmpty());

        StdOut.println("Add first 1");
        deque.addFirst(1);

        StdOut.println("Remove last");
        StdOut.println(deque.removeLast());

        StdOut.println();

        // Add last, remove last
        StdOut.println("Add Last");
        deque.addLast(0);
        StdOut.println("Remove Last");
        deque.removeLast();

        StdOut.println();

        // Add last, remove first
        StdOut.println("Add Last");
        deque.addLast(0);
        StdOut.println("Remove first");
        deque.removeFirst();
    }
}
