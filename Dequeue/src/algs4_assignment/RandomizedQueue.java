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

    private int head;
    private int last;

    private int size;   // The element's amount in the queue

    private Item[] items;

    private static final int INIT_SIZE = 10;

    private void resize(int size) {
        Item[] newItems = (Item[]) new Object[size];

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
        head = (head + 1) % items.length;
        size--;
        return item;
    }

    public Item sample() {
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

        // Normal
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();

        StdOut.println("Insert 20 items.");
        for (int i = 1; i <= 20; i++) {
            randomizedQueue.enqueue(i);
        }

        StdOut.println();

        StdOut.println("Iterate all items at random");
        for (Integer integer : randomizedQueue) {
            StdOut.print(integer);
            StdOut.print(", ");
        }

        StdOut.println();
        StdOut.println();

        StdOut.println("Try another iterator");
        for (Integer integer : randomizedQueue) {
            StdOut.print(integer);
            StdOut.print(", ");
        }

        StdOut.println();
        StdOut.println();

        // Test head > last
        StdOut.println("Test head > last");
        for (int i = 0; i < 5 ; i++) {
            randomizedQueue.dequeue();
            randomizedQueue.enqueue(i);
        }

        StdOut.println();
        StdOut.println("Now iterator it!");
        for (Integer i :
                randomizedQueue) {
            StdOut.print(i);
            StdOut.print(", ");
        }

        StdOut.println();
        StdOut.println();

        StdOut.println("Test the sample() method");
        StdOut.println("The sample is " + randomizedQueue.sample());

        StdOut.println();

        StdOut.println("Remove all the items.");
        while (!randomizedQueue.isEmpty()) {
            StdOut.print(randomizedQueue.dequeue());
            StdOut.print(", ");
        }

        StdOut.println();
        StdOut.println();

        // Special
        if (randomizedQueue.isEmpty()) {
            StdOut.println("Now the queue is empty");
        } else {
            StdOut.println("The queue is not empty");
        }

        StdOut.println();

        StdOut.println("Try to insert one item.");
        randomizedQueue.enqueue(1);
        StdOut.println();

        StdOut.println("Test the queue if is empty");
        StdOut.println("Is the queue empty? " + randomizedQueue.isEmpty());

        StdOut.println();

        StdOut.println("Now remove it.");
        randomizedQueue.dequeue();

        StdOut.println();

        StdOut.println("Test the queue if is empty");
        StdOut.println("Is the queue empty? " + randomizedQueue.isEmpty());

        StdOut.println();

        StdOut.println("Now, reinsert it.");
        randomizedQueue.enqueue(1);

        StdOut.println();

        StdOut.println("Test the queue if is empty");
        StdOut.println("Is the queue empty? " + randomizedQueue.isEmpty());

        StdOut.println();

        StdOut.println("Restore empty");
        while (!randomizedQueue.isEmpty()) {
            randomizedQueue.dequeue();
        }

        // Corner case
        try {
            randomizedQueue.enqueue(null);
        }
        catch (NullPointerException e) {
            StdOut.println("Try to enqueue a null object");
        }

        try {
            randomizedQueue.dequeue();
        }
        catch (NoSuchElementException e) {
            StdOut.println("Try to dequeue from a empty queue");
        }

        try {
            randomizedQueue.iterator().remove();
        }
        catch (UnsupportedOperationException e) {
            StdOut.println("Try to use iterator's remove() method");
        }

    }
}
