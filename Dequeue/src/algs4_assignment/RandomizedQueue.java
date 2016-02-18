package algs4_assignment;

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

    private int head;
    private int last;

    private int size;

    private Item[] items;

    private static final int INIT_SIZE = 10;

    private void resize(int size) {
        Item[] newItems = (Item[]) new Object[size];

        // Linearly iterate the items
        for (int i = 0, j = head; i < newItems.length && j <= last; i++, j = (j + 1) % items.length) {
            newItems[i] = items[j];
        }

        items = newItems;
    }

    public RandomizedQueue() {
        items = (Item[]) new Object[INIT_SIZE];
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

        last = (last + 1) % items.length;
        items[last] = item;
        size++;
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
        head = (head + 1) % items.length;
        size--;
        return item;
    }

    public Item sample() {
        int itemIndex = (StdRandom.uniform(size) + head ) % items.length;
        return items[itemIndex];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }


    private class RandomizedQueueIterator implements Iterator<Item> {

        private int[] shuffleIndexes;

        private int shffleIndexesIterator;

        public RandomizedQueueIterator() {
            shuffleIndexes = new int[size];
            for (int i : shuffleIndexes) {
                shuffleIndexes[i] = i;
            }
            StdRandom.shuffle(shuffleIndexes);
        }

        @Override
        public boolean hasNext() {
            return shffleIndexesIterator < shuffleIndexes.length;
        }

        @Override
        public Item next() {
            if (isEmpty()) {
                throw new NoSuchElementException();
            }

            // TODO: 16/02/19 Iterate in random order.
            /**
             * [ ] 1. Using another new RandomizedQueue Object
             * [x] 2. Using the StdRandom.shuffle() method.
             */
            int index = (shuffleIndexes[shffleIndexesIterator] + head) % items.length;
            shffleIndexesIterator++;
            return items[index];
        }

        @Override
        public void remove() {
            // Not supported
            throw new UnsupportedOperationException();
        }
    }


    public static void main(String [] args) {

    }
}
