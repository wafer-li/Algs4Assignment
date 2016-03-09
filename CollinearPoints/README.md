# Collinear

## Intro
This is the detail of the assignment [3] [Collinear]

The problem of Collinear is to find a line segment which contains at least four points.


**Notice:**  
To use the auto-complete I wrap the src into a package name `algs4_assignment`;  
But to submit the assignment, you need to put the src into the `default` package.

**DO REMEMBER IT!!**

## Solutions

### Brute Force Solution

The brute force solution is quite simple, we just need to iterate the four-tuple and check whether the 3
slope are equal.  

Although the specification says that it won't be more than 4 collinear points at the BruteForceCollinearPoints test,
but I still sort it to prevent the subsegments.

And according to the hints, you could just compare the slope once at the third level loop, and if they are not equal,
you could skip the fourth level loop to reduce the growing level to O(N^3).

### Fast Solutions

The fast solution is a little bit tricky.

First, we need to iterate the whole array, and let each of them be the base point.  
But it is no need to iterate the last 3 points, 
because if the points array only contain no more than 3 points, iterate this whole array will be useless.  
And we could cover the last three points by the countdown fourth point.

Second, it it necessary to **sort** the array **at the very beginning**, 
not only it will be convenient to check whether it **has duplicated points**,  
but also will be convenient to do the iteration.  
But the test do NOT allow us to mutate the input array, so we need to use a copy to replace it.  

But we need to sort it **again** by using the SlopeOrder Comparator, 
and we still need to iterate the array at the **natural sorted order**.

We have two ways to resolve it.

1. Use another copy
2. Sort the array again at the beginning of loop every time. 

How to choose form them? OK, notice that we do need to **sort the array** at the loop one way or another.  
It is just adding another N logN to the array, and inside the loop, its growing level will **still be N logN**,
so it is no need to use anther copy.

Eventually, we reach the important and the difficult part, to find the segments.

**To find the segments, the best way is to use a `start` and a `end` pointer just as the code do.**  
And it will save a lot of your time and energy instead of using a counter.

One of the pros is using a `end` pointer could find the point more convenient and easier.

According to the instructions, the fast solution need to support the collinear points that contain more than 4 points.
Therefore, it will bring us the **subsegments** problem. Not only do we need to find the points at a segment, but we 
need to avoid adding the subsegments.

What is needed is the **longest line segment**, so the **base point must be the smallest, and the end point must be the
largest**.

That is why we need to sort the points at the very beginning.

According to the course, the Java `Arrays.sort()` use **MergeSort** to sort the reference. And MergeSort is **stable**.

Therefore, when we sort the array at the very beginning, it will maintain the relative order between the equal key when
using the Comparator to sort.

Therefore, not only could we just compare the base with the `start` point to check whether the base is the smallest, but 
we could convince that the end point is the largest point.

That is the very crucial part at this assignment.



