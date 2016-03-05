package algs4_assignment;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is the RandomizedQueue Class
 *
 * @author Wafer
 * @version 1.0.0
 * @since 16/02/19 1:27
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INIT_SIZE = 10;

    private int head;
    private int last;

    private int size;   // The element's amount in the queue

    private Item[] items;

    public RandomizedQueue() {
        items = (Item[]) new Object[INIT_SIZE];
    }


    private void resize(int newSize) {
        Item[] newItems = (Item[]) new Object[newSize];

        // Linearly iterate the items
        int i = 0;
        int j;
        for (j = head; j != last; i++, j = (j + 1) % items.length) {
            newItems[i] = items[j];
        }

        // Copy the item in items[last]
        newItems[i] = items[j];

        items = newItems;

        head = 0;
        last = i;
    }


    public boolean isEmpty() {
        return size == 0;
    }


    public int size() {
        return size;
    }


    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (size == items.length) {
            resize(2 * items.length);
        }

        if (isEmpty()) {
            items[last] = item;
            size++;
        } else {
            last = (last + 1) % items.length;
            items[last] = item;
            size++;
        }
    }


    public Item dequeue() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (size < items.length / 4) {
            resize(items.length / 2);
        }

        int itemIndex = (StdRandom.uniform(size) + head) % items.length;
        // Swap with head
        Item item = items[itemIndex];
        items[itemIndex] = items[head];
        items[head] = item;

        // Remove head
        items[head] = null;

        if (head == last) {
            // By now, there is no item in array
            head = 0;
            last = 0;
        }
        else {
            head = (head + 1) % items.length;
        }

        size--;
        return item;
    }


    public Item sample() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int itemIndex = (StdRandom.uniform(size) + head) % items.length;
        return items[itemIndex];
    }


    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }


    private class RandomizedQueueIterator implements Iterator<Item> {

        private int[] shuffleIndexes;

        private int shuffleIndexesIterator;

        public RandomizedQueueIterator() {
            shuffleIndexes = new int[size];
            for (int i = 0; i < size; i++) {
                shuffleIndexes[i] = i;
            }
            StdRandom.shuffle(shuffleIndexes);
        }


        @Override
        public boolean hasNext() {
            return shuffleIndexesIterator < shuffleIndexes.length;
        }


        @Override
        public Item next() {
            if (isEmpty()) {
                throw new NoSuchElementException();
            }

            int index = (shuffleIndexes[shuffleIndexesIterator] + head) % items.length;
            shuffleIndexesIterator++;

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return items[index];
        }


        @Override
        public void remove() {
            // Not supported
            throw new UnsupportedOperationException();
        }
    }


    public static void main(String[] args) {
        // Unit test
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();

        // Enqueue, Dequeue, Sample
        StdOut.println("Is empty? " + randomizedQueue.isEmpty());
        StdOut.println("Enqueue 102");
        randomizedQueue.enqueue(102);
        StdOut.println("Dequeue " + randomizedQueue.dequeue());
        StdOut.println("The size is " + randomizedQueue.size());
        StdOut.println("The size is " + randomizedQueue.size());
        StdOut.println("Enqueue 139");
        randomizedQueue.enqueue(139);
        StdOut.println("The size is " + randomizedQueue.size());
        StdOut.println("The sample is " + randomizedQueue.sample());
    }
}
