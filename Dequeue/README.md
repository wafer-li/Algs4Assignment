# Queue

# Intro
This is the detail of the assignment 2 Randomized Queue & Deque

**Notice:**  
To use the auto-complete I wrap the src into a package name `algs4_assignment`;  
But to submit the assignment, you need to put the src into the `default` package.

**DO REMEMBER IT!!**

# Solutions

This assignment is much easier than the previous one.

## Deque

Accroding to the performance requirement. The best way to implement it is to use the **linked-list**.

Although there is a hint which asks me to use the sentinel node, I decided to use the **double** linked list,
which has two pointers of `prev` and the `next`.  
To support the `removeLast()` method, you need to find the previous node of the last, so use the double linked list is very easy to implement.

There is nothing to say, but I've learn that the best way to implement it is to draw a picture to simulate the insert and delete.  
And with this it can prevent the loitering.

## Randomized Queue

The Randomized Queue is a little more complicate than the Deque.

Accroding to the performance requirement, using the array implementation will be the best.

And because of the array implementation, we need to make the `head` and the `last` pointers have the ability to
travel back to the beginning of the array.  
And because of this it is difficult to check whether the queue is full or empty through these two pointers.

The solutions I take is as below:

1. Use `head = (head + 1) % array.length` to make the pointer travel back.
2. Record the size of items instead of using the pointers to check whether the queue is full or empty.

The second difficult part is to have different independent iterators.

There is a hint of using the `StdRandom.shuffle()`, it can intermingle the order of an sequence.  
Therefore, I use this method to create a random order index list, and use it to iterate the Randomized Queue.
